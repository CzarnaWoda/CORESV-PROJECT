package pl.blackwater.hardcore.tablist;

import pl.blackwater.hardcore.data.User;

public interface TablistVariable
{
    String[] names();

    String get(final User p0);
}
