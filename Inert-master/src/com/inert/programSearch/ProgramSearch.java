package com.inert.programSearch;

import java.io.IOException;
import java.util.Map;

public interface ProgramSearch {
    public Map<String,Program> getPaths() throws IOException, InterruptedException;
}
