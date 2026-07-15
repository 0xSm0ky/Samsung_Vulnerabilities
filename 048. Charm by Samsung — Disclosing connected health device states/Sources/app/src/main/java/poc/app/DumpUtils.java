package poc.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DumpUtils {
    public static void dump(Intent intent, ClassLoader classLoader) {
        if (intent == null) {
            return;
        }
        print(toJson(intent, classLoader));
    }

    public static void dump(Bundle bundle, ClassLoader classLoader) {
        if (bundle == null) {
            return;
        }
        print(toJson(bundle, classLoader));
    }

    private static JsonObject toJson(Intent intent, ClassLoader classLoader) {
        JsonObject o = new JsonObject();
        if (intent.getAction() != null) {
            o.addProperty("action", intent.getAction());
        }
        if (intent.getDataString() != null) {
            o.addProperty("data", intent.getDataString());
        }
        if (intent.getCategories() != null && !intent.getCategories().isEmpty()) {
            o.add("categories", toJson(intent.getCategories()));
        }
        if (intent.getFlags() != 0) {
            o.addProperty("flags", intent.getFlags());
        }
        if (intent.getClipData() != null) {
            o.add("clipData", toJson(intent.getClipData()));
        }
        if (intent.getSelector() != null) {
            o.add("selector", toJson(intent.getSelector(), classLoader));
        }
        if (intent.getExtras() != null) {
            o.add("extras", toJson(intent.getExtras(), classLoader));
        }
        return o;
    }

    private static JsonObject toJson(Bundle bundle, ClassLoader classLoader) {
        JsonObject o = new JsonObject();
        bundle.setClassLoader(classLoader);
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            JsonElement parsedValue;
            if (value instanceof Intent) {
                parsedValue = toJson((Intent) value, classLoader);
            } else if (value instanceof Bundle) {
                parsedValue = toJson((Bundle) value, classLoader);
            } else {
                parsedValue = toJson(value);
            }
            o.add(key, parsedValue);
        }
        return o;
    }

    private static JsonElement toJson(Object value) {
        return getGson().toJsonTree(value);
    }

    private static Gson getGson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    private static Iterable<String> split(String s) {
        return Splitter.fixedLength(500)
                .omitEmptyStrings()
                .split(s);
    }

    private static void print(JsonObject o) {
        String prettyString = getGson().toJson(o);
        for(String data : split(prettyString)) {
            Log.d("evil", data);
        }
    }
}
