package android.app.appchat.adapter;

import android.app.appchat.R;
import android.app.appchat.model.ChatMsg;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class ChatMsgAdapter extends FirebaseRecyclerAdapter<ChatMsg, ChatMsgAdapter.ChatHolder> {

    ArrayList<ChatMsg> chatMsgs;
    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChatMsgAdapter(@NonNull FirebaseRecyclerOptions<ChatMsg> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatHolder holder, int position, @NonNull ChatMsg model) {
        holder.txtMsg.setText(model.getMsg());
        holder.txtTime.setText(model.getTimestamp());
        holder.txtName.setText(model.getUser());
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.received_msg, parent, false);
        return new ChatHolder(view);
    }

    class ChatHolder extends RecyclerView.ViewHolder{

        TextView txtName, txtMsg, txtTime;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            txtMsg = itemView.findViewById(R.id.txtReceiMsg);
            txtName = itemView.findViewById(R.id.txtReceiveDate);
            txtTime = itemView.findViewById(R.id.txtReceiveTime);
        }
    }
}
