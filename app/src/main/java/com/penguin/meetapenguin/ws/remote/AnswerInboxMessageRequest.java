package com.penguin.meetapenguin.ws.remote;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.penguin.meetapenguin.entities.InboxMessage;
import com.penguin.meetapenguin.util.ProfileManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by urbano on 4/22/16.
 */
public class AnswerInboxMessageRequest extends Request<JSONObject> {


    private static final String URL = "http://10.0.3.2:8080/rest";
    private final Response.Listener<String> mListener;
    private final boolean mStatus;

    public AnswerInboxMessageRequest(InboxMessage message, boolean status, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, String.format("%s/messages/%s/status/", URL, message.getId()), errorListener);
        mListener = listener;
        mStatus = status;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("status", String.valueOf(mStatus));
        return parameters;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        params.put("Accept", "application/json");
        params.put("userId", String.valueOf(ProfileManager.getInstance().getUserId()));
        return params;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response.toString());
    }


}
