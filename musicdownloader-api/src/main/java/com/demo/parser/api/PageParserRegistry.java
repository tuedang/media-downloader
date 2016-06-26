package com.demo.parser.api;

import com.demo.parser.CsnParser;
import com.demo.parser.NctParser;
import com.demo.parser.NhacvuiParser;
import com.demo.parser.ZingParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PageParserRegistry {
    private List<MusicParser> registry = new ArrayList<>();

    public PageParserRegistry() {
        register(new CsnParser());
        register(new NctParser());
        register(new ZingParser());
        register(new NhacvuiParser());
    }

    public Optional<MusicParser> lookup(String url) {
        return registry.stream().filter(t -> url.startsWith(t.getDomain())).findFirst();
    }

    public void register(MusicParser musicParser) {
        if (registry.contains(musicParser)) {
            throw new IllegalStateException("Already registered " + musicParser.getDomain());
        }
        registry.add(musicParser);
    }
}
