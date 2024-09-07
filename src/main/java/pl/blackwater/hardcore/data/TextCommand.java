package pl.blackwater.hardcore.data;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class TextCommand {

    @NonNull public String id,command,permission;
    @NonNull public List<String> aliases,text;
}
