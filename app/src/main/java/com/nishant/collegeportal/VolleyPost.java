package com.nishant.collegeportal.collegeportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.Map;

class VolleyPost {
    RequestQueue requestQueue;
    ProgressDialog pd;
    Context context;
    VolleyPost(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        pd = new ProgressDialog(context);
        pd.setMessage("Please wait...");
        pd.setTitle("Processing request");
    }
    void jsonRequest(final Map<String,String> params, final String link)
    {
        pd.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        onResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        onError(error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    void onResult(String response)
    {
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
    }
    void onError(String response)
    {
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
    }
}