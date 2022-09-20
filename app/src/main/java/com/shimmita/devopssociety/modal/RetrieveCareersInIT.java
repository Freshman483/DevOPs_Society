package com.shimmita.devopssociety.modal;

import androidx.annotation.Nullable;

public class RetrieveCareersInIT {

    String Heading;
    String Description;
    @Nullable
    String imagePath;

    //empty constructor required

    public RetrieveCareersInIT() {
    }

    //

    //creating a constructor with parameters context,Heading,description which will help fetching data from firebase
    public RetrieveCareersInIT(String heading, String description, @Nullable String imagePath) {
        this.Heading = heading;
        this.Description = description;
        this.imagePath = imagePath;
    }
    //

    //applying Setters And Getters

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Nullable
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(@Nullable String imagePath) {
        this.imagePath = imagePath;
    }
//

}
