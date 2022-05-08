package com.data.repository;

public interface CustomerMessageRepositoryCustom {
    Integer updateConsentByDialogId(String dialogId,Boolean consent);
}
