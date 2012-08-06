package com.insieme.android;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.insieme.android.constants.InsiemeAndroidConstants;
import com.insieme.android.user.task.impl.LoginTask;
import com.insieme.android.user.task.impl.RegisterTask;
import com.insieme.common.domain.dto.InsiemeExceptionEntity;
import com.insieme.common.domain.dto.UserEntity;
import com.insieme.common.domain.rest.RestResult;

@ContentView(R.layout.activity_login)
public class LoginActivity extends RoboActivity{
	private static final String INSIEME_TEST_FILE = "InsiemeTest";

	@InjectView(R.id.userNameInputId) 
	private EditText userIdInput;
	
	@InjectView(R.id.passwordInputId)
	private EditText userPasswordInput;
	
	@InjectView(R.id.registerEmailInputId)
	private EditText registerEmailInput;
	
	@InjectView(R.id.registerFirstNameInputId)
	private EditText registerFirstNameInput;
	
	@InjectView(R.id.registerLastNameInputId)
	private EditText registerLastNameInput;
	
	@InjectView(R.id.registerPasswordInputId)
	private EditText regiterPasswordInput;
	
	@InjectView(R.id.registerUserInputId)
	private EditText registerUserIdInput;
	
	@Inject
	private Provider<LoginTask> loginTaskProvider;
	
	@Inject
	private Provider<RegisterTask> registerTaskProvider;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    
    /**
     * Login user binding.
     * 
     * TODO: Only takes happy path into account. Need some kind of exception handling wrapper.
     */
    public void loginUser(View v) {
    	try{
	    	String userPassword = userPasswordInput.getText().toString();
			String userId = userIdInput.getText().toString();
    		
			RestResult<UserEntity> loginResult = loginTaskProvider.get().execute(userId, userPassword).get();
    		if (loginResult.isFailure()) {
    			InsiemeExceptionEntity insiemeExceptionEntity = loginResult.getError();
    			Toast.makeText(this, insiemeExceptionEntity.getException(), Toast.LENGTH_LONG).show();
    		} else {
    			UserEntity userEntity = loginResult.getRestResult();
    			goToUserPage(userEntity, v);
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Register user and create common insieme music folder.
     *
     * @param v the view
     */
    public void registerUser(View v) {
    	String userId = registerUserIdInput.getText().toString();
    	String userPassword = regiterPasswordInput.getText().toString();
    	String userFirstName = registerFirstNameInput.getText().toString();
    	String userLastName  = registerLastNameInput.getText().toString();
    	String userEmail = registerEmailInput.getText().toString();
    	
    	try{
	    	RestResult<UserEntity> registerResult = registerTaskProvider.get()
	    			.execute(userId, userPassword, userFirstName, userLastName, userEmail)
	    			.get();
	    	
	    	if (registerResult.isFailure()) {
    			InsiemeExceptionEntity insiemeExceptionEntity = registerResult.getError();
    			Toast.makeText(this, "registration failed: " + insiemeExceptionEntity.getException(), Toast.LENGTH_LONG).show();
    		} else {
    			UserEntity userEntity = registerResult.getRestResult();
    			createInsiemeMusicFolder();
    			goToUserPage(userEntity, v);
    		}
	    	
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.activity_login, menu);
    	return true;
    }
    
    /**
     * Go to user page.
     *
     * @param userEntity the user entity
     * @param v the v
     */
    private void goToUserPage(UserEntity userEntity, View v) {
    	Intent intent = new Intent(v.getContext(), UserActivity.class);
		intent.putExtra(InsiemeAndroidConstants.USER_ID, userEntity);
        startActivityForResult(intent, 0);
    }
    
    /**
     * Creates the insieme music folder used to store local music.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void createInsiemeMusicFolder() throws IOException {
    	String helloInsieme = "Welcome to Insieme!!!";
    	File helloInsiemeTestFile = new File(getFilesDir() + "/" + INSIEME_TEST_FILE);
    	if (helloInsiemeTestFile.exists()) {
    		return;
    	} else {
	    	try {
	    		FileOutputStream fileOutputStream = openFileOutput(INSIEME_TEST_FILE, Context.MODE_PRIVATE);
	    		fileOutputStream.write(helloInsieme.getBytes());
				fileOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
