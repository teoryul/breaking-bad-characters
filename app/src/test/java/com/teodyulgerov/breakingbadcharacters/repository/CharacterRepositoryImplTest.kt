package com.teodyulgerov.breakingbadcharacters.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.teodyulgerov.breakingbadcharacters.api.CharacterApi
import com.teodyulgerov.breakingbadcharacters.persistence.dao.CharacterDao
import com.teodyulgerov.breakingbadcharacters.persistence.model.CharacterPersist
import com.teodyulgerov.breakingbadcharacters.repository.model.CharacterModel
import com.teodyulgerov.breakingbadcharacters.repository.utils.RepoResult
import com.teodyulgerov.breakingbadcharacters.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterRepositoryImplTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var api: CharacterApi

    @Mock
    private lateinit var dao: CharacterDao

    private lateinit var repository: CharacterRepositoryImpl

    private val character = CharacterPersist(
        1,
        "Walter White",
        "url",
        "High School Chemistry Teacher",
        "Presumed dead",
        "Heisenberg",
        "1,2,3,4,5"
    )

    @Before
    fun setup() {
        repository = CharacterRepositoryImpl(api, dao)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `get characters from dao success`() {
        val dbResults = mutableListOf<CharacterPersist>()
        dbResults.add(character)
        whenever(dao.getAllCharacters()).thenReturn(dbResults)

        runBlocking {
            val result = repository.getCharacters()

            assertTrue("Invalid repo result!", result is RepoResult.Success)
            if (result is RepoResult.Success) {
                val resultData: List<CharacterModel> = result.data
                assertEquals(
                    "Invalid repository data!",
                    character.name,
                    resultData[0].name
                )
            }
        }
    }

}