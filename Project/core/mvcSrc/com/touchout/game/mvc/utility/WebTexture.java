package com.touchout.game.mvc.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class WebTexture {
    private final String _url;
    private Texture texture;
    //private volatile byte[] textureBytes;

    public WebTexture(String url, Texture tempTexture) {
        _url = url;
        texture = tempTexture;
        //downloadTextureAsync();
        downloadTextureBytes();
    }

//    private void downloadTextureAsync() {
//        Utils.runInBackground(new Runnable() {
//            @Override
//            public void run() {
//                textureBytes = downloadTextureBytes();
//            }
//        });
//    }

    private void downloadTextureBytes() 
    {
        try 
        {
        	Gdx.app.debug("WebTexture", "sending request with url: " + _url);
        	HttpRequest httpRequest;
        	httpRequest = new HttpRequest(Net.HttpMethods.GET);
			httpRequest.setUrl(_url);
			httpRequest.setContent(null);
			Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
				
				@Override
				public void handleHttpResponse(HttpResponse httpResponse) 
				{
					Gdx.app.debug("WebTexture", "download image ok, start process");
					final byte[] rawImageBytes = httpResponse.getResult();
					Pixmap pixmap = new Pixmap(rawImageBytes, 0, rawImageBytes.length);
					texture = new Texture(pixmap);
					Gdx.app.debug("WebTexture", "process ok, texture updated");
				}
				
				@Override
				public void failed(Throwable t) {
					Gdx.app.debug("WebTexture", "http request failed");
					t.printStackTrace();
				}
				
				@Override
				public void cancelled() {
					Gdx.app.debug("WebTexture", "http request canceled");
				}
			});
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public Texture getTexture() {
        //if (textureBytes != null) processTextureBytes();
        return texture;
    }

//    private void processTextureBytes() {
//        try {
//            Pixmap pixmap = new Pixmap(textureBytes, 0, textureBytes.length);
//            Texture gdxTexture = new Texture(pixmap);
//            gdxTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//            texture = gdxTexture;
//        } catch (Throwable t) {
//            t.printStackTrace();
//        } finally {
//            textureBytes = null;
//        }
//    }
}
