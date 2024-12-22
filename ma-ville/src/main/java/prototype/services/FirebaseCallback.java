package prototype.services;

public interface FirebaseCallback<E> {

    void onSucess();

    void onSucessReturn(E e);

    void onFailure(String message);
}
