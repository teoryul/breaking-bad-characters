package com.teodyulgerov.breakingbadcharacters.ui.fragment.overview

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.viewModelScope
import com.teodyulgerov.breakingbadcharacters.R
import com.teodyulgerov.breakingbadcharacters.repository.CharacterRepositoryImpl
import com.teodyulgerov.breakingbadcharacters.repository.model.CharacterModel
import com.teodyulgerov.breakingbadcharacters.repository.utils.RepoError
import com.teodyulgerov.breakingbadcharacters.repository.utils.RepoResult
import com.teodyulgerov.breakingbadcharacters.ui.fragment.base.BaseVM
import com.teodyulgerov.breakingbadcharacters.utils.BUNDLE_KEY_CHARACTER_DETAIL
import com.teodyulgerov.breakingbadcharacters.utils.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class OverviewVM @Inject constructor() : BaseVM() {

    @Inject
    lateinit var repository: CharacterRepositoryImpl

    /**
     * The character list that is displayed in the RecyclerView.
     */
    val characters = ObservableArrayList<CharacterModel>()

    /**
     * Store all characters for easier filtering.
     */
    val allCharacters = mutableListOf<CharacterModel>()

    /**
     * Store the season filter chosen by the user. 0 means all seasons.
     */
    private var currentSeasonFilter = "0"

    /**
     * Store the current search query string entered by the user.
     */
    private var currentNameFilter: String = ""

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        loadCharacterData()
    }

    fun loadCharacterData() {
        viewModelScope.launch {
            showProgressBar(true)

            when (val result = repository.getCharacters()) {
                is RepoResult.Success -> {
                    if (characters.isNotEmpty()) {
                        characters.clear()
                    }
                    allCharacters.addAll(result.data)
                    characters.addAll(result.data)
                    showProgressBar(false)
                }
                is RepoResult.Error -> {
                    when (result.error) {
                        RepoError.NO_INTERNET -> setLoadingViewText(R.string.internet_connection_unavailable)
                        RepoError.API_UNSUCCESSFUL_REQUEST -> setLoadingViewText(R.string.api_error)
                        RepoError.API_EMPTY_RESPONSE_BODY -> setLoadingViewText(R.string.api_error)
                    }
                }

            }
        }
    }

    fun onItemClick(item: CharacterModel) {
        setNewDestination(item)
    }

    /**
     * Creates a Bundle with the clicked character, creates new destination Event
     * which then is used to navigate the user to the character details screen.
     *
     * @param character The clicked character by the user
     */
    private fun setNewDestination(character: CharacterModel) {
        val bundle = Bundle()
        bundle.putParcelable(BUNDLE_KEY_CHARACTER_DETAIL, character)
        val event = Event(R.id.action_overviewFragment_to_detailFragment, bundle)
        newDestinationEvent.apply { value = event }
    }

    /**
     * Update the season filter and perform the filtering operations.
     *
     * @param filter The new season filter.
     */
    fun filterBySeason(filter: String) {
        if (!"0,1,2,3,4,5".contains(filter) || currentSeasonFilter == filter) {
            return
        }
        currentSeasonFilter = filter
        filter()
    }

    /**
     * Update the name filter and perform the filtering operations.
     *
     * @param filter The new name filter.
     */
    fun filterByName(filter: String?) {
        if (filter == null) {
            return
        }
        val newFilter = filter.trim();
        if (currentNameFilter == newFilter) {
            return
        }
        currentNameFilter = newFilter
        filter()
    }

    /**
     * Performs filtering using both the season and name filter, in that order,
     * and updates the list which is displayed to the user.
     */
    private fun filter() {
        viewModelScope.launch {
            val filteredCharacters = mutableSetOf<CharacterModel>()
            allCharacters.forEach {
                // Filter the characters by season to decrease the list's size
                // 0 all seasons, otherwise filter the specific season
                if (currentSeasonFilter == "0" || it.appearance.contains(currentSeasonFilter)) {
                    // Filter the characters by each entered word
                    // TOOD both words must be contained
                    val words = currentNameFilter.split(" ")
                    var allWordsContained = true
                    words.forEach { word ->
                        if (!it.name.toLowerCase().contains(word.toLowerCase())) {
                            allWordsContained = false
                            return@forEach
                        }
                    }
                    if (allWordsContained) {
                        filteredCharacters.add(it)
                    }
                }
            }

            if (characters.isNotEmpty()) {
                characters.clear()
            }
            if (filteredCharacters.isNotEmpty()) {
                characters.addAll(filteredCharacters)
            }
            // Either display the filtered characters or show a message if the list is empty
            if (characters.isEmpty()) {
                setLoadingViewText(R.string.overview_search_no_results)
            } else {
                showLoadingText(false)
            }
        }
    }

    fun getSeasonFilter(): String = currentSeasonFilter

    fun getNameFilter(): String = currentNameFilter
}