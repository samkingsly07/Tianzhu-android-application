package com.example.tianzhu;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyHandler {
    private static VolleyHandler mVolleyHandler;
    private Context mContext;
    private RequestQueue mrequestQueue;

    private VolleyHandler(Context mContext) {
        this.mContext = mContext;
        this.mrequestQueue = getRequestQueue();
    }
public static synchronized VolleyHandler getInstance(Context Context)
{
    if(mVolleyHandler == null)
    {
        mVolleyHandler = new VolleyHandler(Context);
    }
    return  mVolleyHandler;
}

    public RequestQueue getRequestQueue() {

        if(mrequestQueue == null)
        {
             mrequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mrequestQueue;
    }
    public <T> void addToQueue(Request<T> reg){
      getRequestQueue().add(reg);

    }


}
