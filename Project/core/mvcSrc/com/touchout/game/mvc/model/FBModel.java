package com.touchout.game.mvc.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookError;
import de.tomgrill.gdxfacebook.core.GDXFacebookGraphRequest;
import de.tomgrill.gdxfacebook.core.GDXFacebookGraphResult;
import de.tomgrill.gdxfacebook.core.GDXFacebookLoginResult;
import de.tomgrill.gdxfacebook.core.GDXFacebookSystem;

public class FBModel 
{	
	public String userId;
	public String userName;
	
	private GDXFacebook facebook;
	private FbLoginCallback fbCallback;
	
	public FBModel()	
	{
		Gdx.app.setLogLevel(Application.LOG_DEBUG); 
		
		GDXFacebookConfig fbConfig = new GDXFacebookConfig();
		fbConfig.PREF_FILENAME = ".facebookSessionData";
		fbConfig.APP_ID = "1661787184068606";
		
		facebook = GDXFacebookSystem.install(fbConfig);
	}
	
	public interface FbLoginCallback
	{
		void success();
	}
	
	public void login(FbLoginCallback callback)
	{
		fbCallback = callback;
		
		List<String> permissions = new ArrayList<String>();
		permissions.add("email");
		permissions.add("public_profile");
		permissions.add("user_friends");

		facebook.loginWithReadPermissions(permissions, new GDXFacebookCallback<GDXFacebookLoginResult>() {
		    @Override
		    public void onSuccess(GDXFacebookLoginResult result) {
		        // Login successful
		    	Gdx.app.debug("FB", 
		    			"READ REQUEST: User logged in successfully. User ID: " + result.getAccessToken().getUserId());
		    	userId = result.getAccessToken().getUserId();
		    	
		    	/* Building your request */
				GDXFacebookGraphRequest request = new GDXFacebookGraphRequest();
				request.setNode("me"); // call against the "me" node. 
				request.useCurrentAccessToken(); // Set this if you want gdx-facebook to use the currently cached access token.

				/* Sending the request */
				facebook.newGraphRequest(request, new GDXFacebookCallback<GDXFacebookGraphResult>() {

				    @Override
				    public void onSuccess(GDXFacebookGraphResult result) {
				        // Parse Facebook Graph API repsond
				        JsonValue root = new JsonReader().parse(result.getResultAsJson());
				        // In a real project you should do some validation of the JsonValue. You never know what comes back :)
				        userId = root.getString("id");
				        userName = root.getString("name");
				        
				        Gdx.app.debug("FB", root.toString());
				        Gdx.app.debug("FB", "name first: " + String.format("%x", userName.getBytes()[0]));	
				        
				        fbCallback.success();
				    }

				    @Override
				    public void onError(GDXFacebookError error) {
				        // Handle request errors here
//				    	/JsonValue root = new JsonReader().parse(error.getErrorMessage().getResultAsJson());
				    	Gdx.app.debug("FB", "Error:" + error.getErrorMessage());
				    }

				    @Override
				    public void onCancel() {
				        // Handle request cancels
				    	Gdx.app.debug("FB", "Canceled");
				    }

				    @Override
				    public void onFail(Throwable t) {
				        // Handle request fails here
				    	Gdx.app.debug("FB", "Failed:" + t.getLocalizedMessage());				    	
				    }

				});
		    }

		    @Override
		    public void onError(GDXFacebookError error) {
		        // Error handling
		    }

		    @Override
		    public void onCancel() {
		        // When the user cancels the login process
		    }

		    @Override
		    public void onFail(Throwable t) {
		        // When the login fails
		    }
		});
	}

}
