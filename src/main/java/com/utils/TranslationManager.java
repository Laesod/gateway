package com.utils;

import com.entity.TranslationEntity;
import com.entity.TranslationMapEntity;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashSet;
import java.util.Iterator;
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

    public String getTranslation(Set<TranslationEntity> translations, String field, String language){
        String translationContent = "";

        Iterator<TranslationEntity> iteratorForTranslations = translations.iterator();
        TranslationEntity translation = new TranslationEntity();
        while (iteratorForTranslations.hasNext()) {
            translation = iteratorForTranslations.next();

            if (translation.getField().equals(field) && translation.getLanguage().equals(language)) {
                translationContent = translation.getContent();
            }
        }

        return translationContent;
    }
}
