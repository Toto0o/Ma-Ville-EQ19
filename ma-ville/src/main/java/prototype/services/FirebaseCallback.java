package prototype.services;

public interface FirebaseCallback<T> {
    void onSuccess();
    void onSuccessReturn(T result);  // Correct spelling to match your method name in NotificationService
    void onFailureReturn(String errorMessage);
}
