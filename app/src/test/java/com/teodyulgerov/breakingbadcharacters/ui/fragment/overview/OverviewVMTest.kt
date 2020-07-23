package com.teodyulgerov.breakingbadcharacters.ui.fragment.overview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.teodyulgerov.breakingbadcharacters.repository.CharacterRepositoryImpl
import com.teodyulgerov.breakingbadcharacters.repository.model.CharacterModel
import com.teodyulgerov.breakingbadcharacters.repository.utils.RepoError
import com.teodyulgerov.breakingbadcharacters.repository.utils.RepoResult
import com.teodyulgerov.breakingbadcharacters.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class OverviewVMTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: CharacterRepositoryImpl

    private lateinit var overviewVM: OverviewVM

    @Before
    fun setup() {
        overviewVM = OverviewVM()
        overviewVM.repository = repository

        // Seasons are between 1-5, 0 - all seasons
        overviewVM.allCharacters.add(CharacterModel("Brad", "", "", "", "", "1"))
        overviewVM.allCharacters.add(CharacterModel("Stacy", "", "", "", "", "2"))

        overviewVM.characters.add(CharacterModel("Brad", "", "", "", "", "1"))
        overviewVM.characters.add(CharacterModel("Stacy", "", "", "", "", "2"))
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `load character data success`() {
        // Should start empty
        overviewVM.allCharacters.clear()

        val characterData = mutableListOf<CharacterModel>()
        characterData.add(CharacterModel("", "", "", "", "", ""))
        val repoResult = RepoResult.Success(characterData)

        testCoroutineRule.runBlockingTest {
            whenever(repository.getCharacters()).doReturn(repoResult)
            overviewVM.loadCharacterData()
        }

        assertEquals(
            "Returned incorrect list!",
            characterData[0],
            overviewVM.allCharacters[0]
        )
        assertEquals(
            "Returned incorrect list!",
            characterData[0],
            overviewVM.characters[0]
        )
    }

    @Test
    fun `load character data error`() {
        // Should start empty
        overviewVM.allCharacters.clear()
        overviewVM.characters.clear()

        val repoResult = RepoResult.Error(RepoError.NO_INTERNET)

        testCoroutineRule.runBlockingTest {
            whenever(repository.getCharacters()).doReturn(repoResult)
            overviewVM.loadCharacterData()
        }

        assertEquals("List size must be null!", 0, overviewVM.allCharacters.size)
        assertEquals("List size must be null!", 0, overviewVM.characters.size)
    }

    @Test
    fun `filter by incorrect season`() {
        var incorrectFilter = "-1"
        testCoroutineRule.runBlockingTest {
            overviewVM.filterBySeason(incorrectFilter)
        }

        assertNotEquals(
            "Season filter must have not been updated!",
            "-1",
            overviewVM.getSeasonFilter()
        )

        incorrectFilter = "6"
        testCoroutineRule.runBlockingTest {
            overviewVM.filterBySeason(incorrectFilter)
        }

        assertNotEquals(
            "Season filter must have not been updated!",
            incorrectFilter,
            overviewVM.getSeasonFilter()
        )
        assertEquals("Characters list size must have not changed!", 2, overviewVM.characters.size)
    }

    @Test
    fun `filter by correct season`() {
        val correctFilter = "1"
        testCoroutineRule.runBlockingTest {
            overviewVM.filterBySeason(correctFilter)
        }

        assertEquals(
            "Season filter must have been updated!",
            correctFilter,
            overviewVM.getSeasonFilter()
        )
        assertEquals(
            "All characters list size must have not changed!",
            2,
            overviewVM.allCharacters.size
        )
        assertEquals("Characters list size must have changed!", 1, overviewVM.characters.size)
    }

    @Test
    fun `filter by empty string`() {
        val filter = "" // All names
        testCoroutineRule.runBlockingTest {
            overviewVM.filterByName(filter)
        }

        assertEquals(
            "Name filter must have been updated!",
            filter,
            overviewVM.getNameFilter()
        )
        assertEquals(
            "All characters list size must have not changed!",
            2,
            overviewVM.allCharacters.size
        )
        assertEquals("Characters list size must have not changed!", 2, overviewVM.characters.size)
    }

    @Test
    fun `filter by name`() {
        val filter = "br" // Brad
        testCoroutineRule.runBlockingTest {
            overviewVM.filterByName(filter)
        }

        assertEquals(
            "Name filter must have been updated!",
            filter,
            overviewVM.getNameFilter()
        )
        assertEquals(
            "All characters list size must have not changed!",
            2,
            overviewVM.allCharacters.size
        )
        assertEquals("Characters list size must have changed!", 1, overviewVM.characters.size)
    }

    @Test
    fun `filter consecutively by season, name, season, name`() {
        var seasonFilter = "1"
        var nameFilter = "st" // Stacy

        testCoroutineRule.runBlockingTest {
            // Season 1 -> only Brad
            overviewVM.filterBySeason(seasonFilter)
            assertEquals("Characters list size must have changed!", 1, overviewVM.characters.size)

            // Season 1 -> filter for Stacy -> no results
            overviewVM.filterByName(nameFilter)
            // Trying to filter for Stacy but she is in season 2
            assertEquals("Characters list size must have changed!", 0, overviewVM.characters.size)

            // Season 2 -> Still filtering for Stacy -> 1 result
            seasonFilter = "2"
            overviewVM.filterBySeason(seasonFilter)
            assertEquals("Characters list size must have changed!", 1, overviewVM.characters.size)
            assertEquals("Incorrect filtering!", "Stacy", overviewVM.characters[0].name)

            // Season 2 -> Filter for Brad -> no results
            nameFilter = "br" // Brad
            overviewVM.filterByName(nameFilter)
            assertEquals("Characters list size must have changed!", 0, overviewVM.characters.size)

            // Season 2 -> Reset name filter -> 1 results (Stacy)
            nameFilter = ""
            overviewVM.filterByName(nameFilter)
            assertEquals("Characters list size must have changed!", 1, overviewVM.characters.size)
            assertEquals("Incorrect filtering!", "Stacy", overviewVM.characters[0].name)

            // Reset season filter -> All names -> 2 results
            seasonFilter = "0"
            overviewVM.filterBySeason(nameFilter)
            assertEquals("Characters list size must have changed!", 2, overviewVM.characters.size)
        }
    }

}