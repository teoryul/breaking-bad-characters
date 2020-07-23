package com.teodyulgerov.breakingbadcharacters.ui.fragment.detail

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.teodyulgerov.breakingbadcharacters.R
import com.teodyulgerov.breakingbadcharacters.repository.model.CharacterModel
import com.teodyulgerov.breakingbadcharacters.ui.fragment.base.BaseVM
import com.teodyulgerov.breakingbadcharacters.utils.BUNDLE_KEY_CHARACTER_DETAIL
import javax.inject.Inject

class DetailVM @Inject constructor() : BaseVM() {

    val character = MutableLiveData<CharacterModel>()

    val actionBarTitle = MutableLiveData<String>().apply { postValue("") }

    fun setCharacter(bundle: Bundle?) {
        bundle?.let {
            val characterModel = bundle.getParcelable<CharacterModel>(BUNDLE_KEY_CHARACTER_DETAIL);
            if (characterModel != null) {
                character.value = characterModel
                actionBarTitle.value = characterModel.name
                return
            }
        }
        setLoadingViewText(R.string.unable_to_load_character_info)
    }
}