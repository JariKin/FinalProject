package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.eventViewHolder> {
    private Context mContext;
    private ArrayList<eventItem> mEventList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public eventAdapter(Context context, ArrayList<eventItem> eventList) {
        mContext = context;
        mEventList = eventList;
    }

    @Override
    public eventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.event_item, parent, false);
                return new eventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(eventViewHolder holder, int position) {
        eventItem currentItem = mEventList.get(position);

        String info_url = currentItem.getInfo();
        String name = currentItem.getName();
        String location_extra_info = currentItem.getLocation();
        String short_description = currentItem.getSDescription();


        holder.mTextViewInfo.setText(info_url);
        holder.mTextViewName.setText("Name: " + name);
        holder.mTextViewLocation.setText("Location: " + location_extra_info);
        holder.mTextViewSDescription.setText("Short description: " + short_description);

    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public class eventViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewInfo;
        public TextView mTextViewName;
        public TextView mTextViewLocation;
        public TextView mTextViewSDescription;

        public eventViewHolder(View itemView) {
            super(itemView);
            mTextViewInfo = itemView.findViewById(R.id.text_view_info_url);
            mTextViewName = itemView.findViewById(R.id.text_view_name);
            mTextViewLocation = itemView.findViewById(R.id.text_view_location);
            mTextViewSDescription = itemView.findViewById(R.id.text_view_short_description);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
