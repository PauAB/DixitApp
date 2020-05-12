package com.example.dixitapp;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonInterestParser {
    public List<InterestEntity> readJsonStream(InputStream in) throws IOException
    {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        try
        {
            return readInterestsArray(reader);
        }
        finally {
            reader.close();
        }
    }

    public List<InterestEntity> readInterestsArray(JsonReader reader) throws IOException
    {
        List<InterestEntity> interests = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext())
        {
            interests.add(readInterest(reader));
        }
        reader.endArray();
        return interests;
    }

    public InterestEntity readInterest(JsonReader reader) throws IOException
    {
        Integer id = 0;
        String image = "";
        String username = "";
        String category = "";
        String text = "";
        String dateAndTime = "";

        Integer counter = 0;

        reader.beginObject();

        while(reader.hasNext())
        {
            String streamName = reader.nextName();
            switch (streamName)
            {
                case "id":
                    id = reader.nextInt();
                    break;
                case "image":
                    image = reader.nextString();
                    break;
                case "username":
                    username = reader.nextString();
                    break;
                case "dateAndTime":
                    dateAndTime = reader.nextString();
                    break;
                case "category":
                    category = reader.nextString();
                    break;
                case "text":
                    text = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }

        reader.endObject();
        return new InterestEntity(id, image, username, dateAndTime, category, text, counter);
    }
}
