package com.utils;

import com.dto.RoleResponseDto;
import com.entity.RoleEntity;
import com.entity.TranslationEntity;
import com.entity.TranslationMapEntity;
import com.entity.UserEntity;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 20/02/16.
 */
public class TranslationManager {
    private String[] supportedLanguages = {"English", "French"};

    public Set<TranslationEntity> addTranslation(String content, String parentEntity, String field, TranslationMapEntity translationMap){
       Set<TranslationEntity> translations = new HashSet<>();

        for (String language : this.supportedLanguages) {
            TranslationEntity translation = new TranslationEntity();
            translation.setContent("");
            if (language.equals(LocaleContextHolder.getLocale().getDisplayLanguage())) {
                translation.setContent(content);
            }
            translation.setParentEntity(parentEntity);
            translation.setField(field);
            translation.setLanguage(language);
            translation.setTranslationMap(translationMap);

            translations.add(translation);
        }

        return translations;
    }
}
