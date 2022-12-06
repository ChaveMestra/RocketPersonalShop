package com.rocketmc.personalshops.prompt;

public interface PromptMechanics {

    void onStart();

    void onCancel();

    void onFailure(int failureCode);

    void onSuccess();

    void onReceiveResponse();

}
