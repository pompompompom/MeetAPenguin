package com.penguin.meetapenguin.ws.remote;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.penguin.meetapenguin.util.ProfileManager;
import com.penguin.meetapenguin.util.ServerConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by urbano on 4/22/16.
 */
public class RequestContactRenew extends Request<String> {


    private static final String URL = ServerConstants.SERVER_URL;
    private final Response.Listener<String> mListener;
    Map<String, String> mParams;

    public RequestContactRenew(Integer contactId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, String.format("%s/messages?contactId=%d", URL, contactId), errorListener);
        mListener = listener;
        mParams = new HashMap<String, String>();
    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        return Response.success(jsonString,
                HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", String.valueOf(ProfileManager.getInstance().getUserId()));
        return params;
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response.toString());
    }


}
