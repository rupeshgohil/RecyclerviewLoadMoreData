package rps.recyclerviewloadmoredata;

import java.io.Serializable;

class MovieModal implements Serializable {
    String title;
    String rating;
    String type;

    public MovieModal(String type) {
        this.type = type;
    }
}