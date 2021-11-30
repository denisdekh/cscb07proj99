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

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTest {

    @Mock
    LoginActivity view;

    @Mock
    LoginModel model;

    @Test
    public void testPresenterValidValues() {
        when(view.getUsername()).thenReturn("UnitTest1");
        when(view.getPassword()).thenReturn("test1password");

        LoginPresenter presenter = new LoginPresenter(model, view);
        presenter.checkAccount();

        verify(model).accountExists(view.getUsername(), view.getPassword(), view);
    }

    @Test
    public void testPresenterNoUsername() {
        when(view.getUsername()).thenReturn("");
        when(view.getPassword()).thenReturn("test1password");

        LoginPresenter presenter = new LoginPresenter(model, view);
        presenter.checkAccount();

        verify(view).displayMessage("Username field is empty");
    }

    @Test
    public void testPresenterNoPassword() {
        when(view.getUsername()).thenReturn("UnitTest1");
        when(view.getPassword()).thenReturn("");

        LoginPresenter presenter = new LoginPresenter(model, view);
        presenter.checkAccount();

        verify(view).displayMessage("Password field is empty");
    }

    @Test
    public void testPresenterNoUsernameNoPassword() {
        when(view.getUsername()).thenReturn("");
        when(view.getPassword()).thenReturn("");

        LoginPresenter presenter = new LoginPresenter(model, view);
        presenter.checkAccount();

        verify(view).displayMessage("Username field is empty");
    }
}
