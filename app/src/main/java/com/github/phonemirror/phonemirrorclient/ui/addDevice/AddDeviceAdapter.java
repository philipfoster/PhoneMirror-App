package com.github.phonemirror.phonemirrorclient.ui.addDevice;

import android.support.v7.widget.AppCompatImageButton;
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

    public AddDeviceAdapter(OnDeviceInteractionListener listener) {
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
        holder.data = values.get(position);
        holder.idView.setText(values.get(position).getIpAddress().toString());
        holder.contentView.setText(values.get(position).getName());
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
        private final AppCompatImageButton imageButton;
        private Device data;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            idView = (TextView) view.findViewById(R.id.id);
            contentView = (TextView) view.findViewById(R.id.content);
            imageButton = (AppCompatImageButton) view.findViewById(R.id.connectButton);

            imageButton.setOnClickListener(v -> Timber.d("onClick called on list item for %s", data.toString()));
        }

    }
}
