package com.example.cscb07app;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.cscb07app.login.LoginActivity;
import com.example.cscb07app.login.LoginModel;
import com.example.cscb07app.login.LoginPresenter;
import com.example.cscb07app.login.RegisterActivity;
import com.example.cscb07app.login.RegisterModel;
import com.example.cscb07app.login.RegisterPresenter;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterUnitTest {

    @Mock
    RegisterActivity view;

    @Mock
    RegisterModel model;

    @Mock
    RegisterPresenter presenter_mock;

    @Test
    public void testPresenterValidValues() {
        when(view.getUsername()).thenReturn("UnitTest1");
        when(view.getPassword()).thenReturn("test1password");
        when(view.getConfirmPassword()).thenReturn("test1password");
        when(view.getEmail()).thenReturn("test@gmail.com");
        when(view.getName()).thenReturn("test");
        when(view.getUserType()).thenReturn("Customer");


        RegisterPresenter presenter = new RegisterPresenter(model, view);
        presenter.checkAccount();

        verify(model).accountCreate(view.getName(), view.getEmail(),view.getUsername(),view.getPassword(), view.getUserType(), view);
    }

    @Test
    public void testPresenterNoUsername() {
        when(view.getUsername()).thenReturn("");
        when(view.getPassword()).thenReturn("test1password");
        when(view.getEmail()).thenReturn("test@gmail.com");
        when(view.getName()).thenReturn("test");
        when(view.getUserType()).thenReturn("Customer");

        RegisterPresenter presenter = new RegisterPresenter(model, view);
        presenter.checkAccount();

        verify(view).displayMessage("Not a valid username", "username");
    }

    @Test
    public void testPresenterInvalidUsername() {
        when(view.getUsername()).thenReturn("@#asd231@");
        when(view.getPassword()).thenReturn("test1password");
        when(view.getEmail()).thenReturn("test@gmail.com");
        when(view.getName()).thenReturn("test");
        when(view.getUserType()).thenReturn("Customer");

        RegisterPresenter presenter = new RegisterPresenter(model, view);
        presenter.checkAccount();

        verify(view).displayMessage("Not a valid username", "username");
    }

    @Test
    public void testPresenterNoPassword() {
        when(view.getUsername()).thenReturn("testusername");
        when(view.getPassword()).thenReturn("");
        when(view.getEmail()).thenReturn("test@gmail.com");
        when(view.getName()).thenReturn("test");
        when(view.getUserType()).thenReturn("Customer");

        RegisterPresenter presenter = new RegisterPresenter(model, view);
        presenter.checkAccount();

        verify(view).displayMessage("Not a valid password", "password");
    }

    @Test
    public void testPresenterInvalidPassword() {
        when(view.getUsername()).thenReturn("testusername");
        when(view.getPassword()).thenReturn("a");
        when(view.getEmail()).thenReturn("test@gmail.com");
        when(view.getName()).thenReturn("test");
        when(view.getUserType()).thenReturn("Customer");

        RegisterPresenter presenter = new RegisterPresenter(model, view);
        presenter.checkAccount();

        verify(view).displayMessage("Not a valid password", "password");
    }

    @Test
    public void testPresenterPasswordNotEqual() {
        when(view.getUsername()).thenReturn("testusername");
        when(view.getPassword()).thenReturn("test1");
        when(view.getConfirmPassword()).thenReturn("test2");
        when(view.getEmail()).thenReturn("test@gmail.com");
        when(view.getName()).thenReturn("test");
        when(view.getUserType()).thenReturn("Customer");

        RegisterPresenter presenter = new RegisterPresenter(model, view);
        presenter.checkAccount();

        verify(view).displayMessage("Passwords do not match", "password");
    }

    @Test
    public void testPresenterNoName() {
        when(view.getUsername()).thenReturn("testusername");
        when(view.getPassword()).thenReturn("test1password");
        when(view.getEmail()).thenReturn("test@gmail.com");
        when(view.getName()).thenReturn("");
        when(view.getUserType()).thenReturn("Customer");

        RegisterPresenter presenter = new RegisterPresenter(model, view);
        presenter.checkAccount();

        verify(view).displayMessage("Not a valid name", "name");
    }

    @Test
    public void testPresenterInvalidName() {
        when(view.getUsername()).thenReturn("testusername");
        when(view.getPassword()).thenReturn("test1password");
        when(view.getEmail()).thenReturn("test@gmail.com");
        when(view.getName()).thenReturn("@#asd12@#");
        when(view.getUserType()).thenReturn("Customer");

        RegisterPresenter presenter = new RegisterPresenter(model, view);
        presenter.checkAccount();

        verify(view).displayMessage("Not a valid name", "name");
    }

    @Test
    public void testPresenterNoEmail() {
        when(view.getUsername()).thenReturn("testusername");
        when(view.getPassword()).thenReturn("test1password");
        when(view.getEmail()).thenReturn("");
        when(view.getName()).thenReturn("test");
        when(view.getUserType()).thenReturn("Customer");

        RegisterPresenter presenter = new RegisterPresenter(model, view);
        presenter.checkAccount();

        verify(view).displayMessage("Not a valid email", "email");
    }

    @Test
    public void testPresenterInvalidEmail() {
        when(view.getUsername()).thenReturn("testusername");
        when(view.getPassword()).thenReturn("test1password");
        when(view.getEmail()).thenReturn("123123.com");
        when(view.getName()).thenReturn("test");
        when(view.getUserType()).thenReturn("Customer");

        RegisterPresenter presenter = new RegisterPresenter(model, view);
        presenter.checkAccount();

        verify(view).displayMessage("Not a valid email", "email");
    }
}
