package com.teodyulgerov.breakingbadcharacters.repository

import android.util.Log
import com.teodyulgerov.breakingbadcharacters.api.CharacterApi
import com.teodyulgerov.breakingbadcharacters.api.model.CharacterResponse
import com.teodyulgerov.breakingbadcharacters.persistence.dao.CharacterDao
import com.teodyulgerov.breakingbadcharacters.persistence.model.CharacterPersist
import com.teodyulgerov.breakingbadcharacters.repository.model.CharacterModel
import com.teodyulgerov.breakingbadcharacters.repository.utils.RepoError
import com.teodyulgerov.breakingbadcharacters.repository.utils.RepoResult
import com.teodyulgerov.breakingbadcharacters.utils.NetworkConnectivityCheckerImpl.isNetworkAvailable
import com.teodyulgerov.breakingbadcharacters.utils.convertStringListToBulletedString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class CharacterRepositoryImpl
@Inject constructor(
    private val api: CharacterApi,
    private val dao: CharacterDao
) : CharacterRepository {

    companion object {
        val TAG = CharacterRepository::class.java.simpleName
    }

    override suspend fun getCharacters(): RepoResult<List<CharacterModel>> {
        var result: RepoResult<List<CharacterModel>>

        withContext(Dispatchers.IO) {
            // Get the characters from the local database
            try {
                val charactersCache = dao.getAllCharacters()
                if (charactersCache.isNotEmpty()) {
                    result = RepoResult.Success(convertPersistToCharacterModel(charactersCache))
                    return@withContext
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to retrieve data from cache!", e)
            }

            // Local database is empty. Get the data from the api.
            if (!isNetworkAvailable()) {
                result = RepoResult.Error(RepoError.NO_INTERNET)
                return@withContext
            }

            val charactersResponse: Response<List<CharacterResponse>>
            try {
                charactersResponse = api.getCharacters()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to make api request!", e)
                result = RepoResult.Error(RepoError.API_UNSUCCESSFUL_REQUEST)
                return@withContext
            }
            if (!charactersResponse.isSuccessful) {
                result = RepoResult.Error(RepoError.API_UNSUCCESSFUL_REQUEST)
                return@withContext
            }

            charactersResponse.body()?.let { it ->
                // Check if the response body is empty, the list is empty
                if (it.isEmpty()) {
                    result = RepoResult.Error(RepoError.API_EMPTY_RESPONSE_BODY)
                    return@withContext
                }

                val filteredCharacters = filterResponseByCategory(it)
                convertResponseToCharacterPersist(filteredCharacters).let {
                    try {
                        dao.insertListWithReplaceStrategy(it)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to persist api response!", e)
                    }

                }

                result = RepoResult.Success(convertResponseToCharacterModel(filteredCharacters))
                return@withContext
            }

            result = RepoResult.Error(RepoError.API_EMPTY_RESPONSE_BODY)
        }

        return result
    }

    /**
     * Filter the api response data by category.
     * @return List containing characters that have appeared only in "Breaking Bad", not in "Better Call Saul".
     */
    private fun filterResponseByCategory(response: List<CharacterResponse>): List<CharacterResponse> {
        return response.filter {
            it.category.contains("Breaking Bad")
        }
    }

    /**
     * Convert the api response data into a list of CharacterPersist to be stored in the local database.
     */
    private fun convertResponseToCharacterPersist(response: List<CharacterResponse>): List<CharacterPersist> {
        return response.map {
            CharacterPersist(
                it.charId,
                it.name,
                it.img,
                it.occupation.toString(),
                it.status,
                it.nickname,
                it.appearance.toString()
            )
        }
    }

    /**
     * Convert the cached data into a list of CharacterModel to be displayed in the UI.
     */
    private fun convertPersistToCharacterModel(cachedData: List<CharacterPersist>): List<CharacterModel> {
        return cachedData.map { it ->
            CharacterModel(
                it.name,
                it.img,
                convertStringListToBulletedString(
                    it.occupation
                        .trim()
                        .removePrefix("[")
                        .removeSuffix("]")
                        .split(",")
                        .map { it.trim() }),
                it.status,
                it.nickname,
                it.appearance.trim().removePrefix("[").removeSuffix("]")
            )
        }
    }

    /**
     * Convert the api response data into a list of CharacterModel to be displayed in the UI.
     */
    private fun convertResponseToCharacterModel(response: List<CharacterResponse>): List<CharacterModel> {
        return response.map {
            CharacterModel(
                it.name,
                it.img,
                convertStringListToBulletedString(it.occupation),
                it.status,
                it.nickname,
                it.appearance.toString().removePrefix("[").removeSuffix("]")
            )
        }
    }
}