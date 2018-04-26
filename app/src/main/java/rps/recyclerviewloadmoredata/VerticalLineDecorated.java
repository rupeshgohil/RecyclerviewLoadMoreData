package rps.recyclerviewloadmoredata;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class VerticalLineDecorated extends RecyclerView.ItemDecoration {
    int Space = 0;
    public VerticalLineDecorated(int i) {
        this.Space = i;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildAdapterPosition(view) == 0)
            outRect.top = Space;

        outRect.bottom = Space;
    }
}
