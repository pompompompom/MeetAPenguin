package com.penguin.meetapenguin.ws.remote;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by urbano on 2/9/16.
 */
public class CreateEntityRequest extends Request<String> {
    private static final String API_URL = "";
    private final Response.Listener<String> mListener;
    private final HashMap mParameter;

    public CreateEntityRequest(HashMap<String, String> parameter, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, API_URL, errorListener);
        mListener = listener;
        mParameter = parameter;
    }

    @Override
    public Map<String, String> getParams() {
        return mParameter;
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
        params.put("Content-Type", "application/json");
        return params;
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}

