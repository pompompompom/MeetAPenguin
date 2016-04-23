package com.penguin.meetapenguin.ws.remote;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.penguin.meetapenguin.entities.InboxMessage;
import com.penguin.meetapenguin.util.ProfileManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by urbano on 4/22/16.
 */
public class AnswerInboxMessageRequest extends Request<String> {


    private static final String URL = "http://10.0.3.2:8080/rest";
    private final Response.Listener<String> mListener;
    Map<String, String> mParams;

    public AnswerInboxMessageRequest(InboxMessage message, boolean status, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, String.format("%s/messages/%s/status", URL, message.getId()), errorListener);
        mListener = listener;
        mParams = new HashMap<String, String>();
        mParams.put("accepted", String.valueOf(status));
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
