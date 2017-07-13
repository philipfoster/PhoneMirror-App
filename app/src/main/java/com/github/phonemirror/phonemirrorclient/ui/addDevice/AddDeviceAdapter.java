package com.github.phonemirror.phonemirrorclient.ui.addDevice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.phonemirror.phonemirrorclient.R;
import com.github.phonemirror.phonemirrorclient.data.Device;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * The adapter for the {@link RecyclerView} in {@link AddDeviceFragment}
 */
class AddDeviceAdapter extends RecyclerView.Adapter<AddDeviceAdapter.ViewHolder> {

    private final List<Device> values;
    private final OnDeviceInteractionListener interactionListener;

    AddDeviceAdapter(OnDeviceInteractionListener listener) {
        values = new ArrayList<>();
        interactionListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_device_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.changeContent(values.get(position));
    }

    /**
     * Replace the data being displayed by the adapter.
     * Note: this method will automatically call {@link #notifyDataSetChanged()}, so doing it
     * manually is unnecessary.
     * @param data the new list of devices.
     */
    void updateData(List<Device> data) {
        // at some point, it may be better/more efficient to consider a smarter merging strategy
        values.clear();
        values.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final TextView idView;
        private final TextView contentView;
        private Device data;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            idView = (TextView) view.findViewById(R.id.id);
            contentView = (TextView) view.findViewById(R.id.content);

            view.setOnClickListener(v -> interactionListener.onItemClicked(data));
        }

        void changeContent(Device device) {
            data = device;
            updateUi();

        }

        private void updateUi() {
            Timber.d("updateUi() called");
            idView.setText(data.getIpAddress().toString());
            contentView.setText(data.getName());
        }
    }
}
