package coms.kw.ac.kr.server.service.article.contentwrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Converter {

    public static final String ESCAPE_CHAR = "$";
    public static final String REPLACEMENT_CHAR = "$!";
    public static final String EMBED_PREFIX = "$e";
    public static final String LINE_BREAK = "$n";
    public static final String NULL_ATTRIBUTE_CHAR = " ";

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Delta> jsonToDelta(String content) {
        List<Delta> delta = new ArrayList<Delta>();
        JsonNode ops;
        try {
            ops = mapper.readTree(content).get("ops");
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }

        if (ops.isArray()) { // Non-single line article
            for (final JsonNode node : ops) {
                Attributes attr = null;
                if (node.hasNonNull("attributes")) {
                    try {
                        attr = mapper.treeToValue(node.get("attributes"), Attributes.class);
                    } catch (IOException exception) {
                        attr = new Attributes();
                    }
                }

                if (node.get("insert").isValueNode())
                    delta.add(new Delta(attr, node.get("insert").asText()));
                else {
                    delta.add(new Delta(attr, node.get("insert")));
                }
            }
        } else { // Single line article
            Attributes attr = null;
            if (ops.hasNonNull("attributes")) {
                try {
                    attr = mapper.treeToValue(ops.get("attributes"), Attributes.class);
                } catch (IOException exception) {
                    attr = new Attributes();
                }
            }

            delta.add(new Delta(attr, ops.get("insert").asText()));
        }

        return delta;
    }

    public static String deltaToJson(List<Delta> deltas) {
        try {
            ArrayNode ops = mapper.valueToTree(deltas);
            JsonNode node = mapper.createObjectNode().set("ops", ops);
            return mapper.writeValueAsString(node);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public static SerializedContent deltaToString(List<Delta> deltas) {
        StringBuilder insert = new StringBuilder(1024);
        StringBuilder attributes = new StringBuilder(512);
        for (Delta d : deltas) {
            insert.append(stringfyInsert(d));
            attributes.append(stringfyAttributes(d));
        }

        return new SerializedContent(insert.toString(), attributes.toString());
    }

    public static List<Delta> stringToDelta(SerializedContent serializedContent) {
        String[] insertSplit = serializedContent.getInsert().split(Pattern.quote(LINE_BREAK));
        String[] attrSplit = serializedContent.getAttributes().split(Pattern.quote(LINE_BREAK));

        List<Attributes> attributes = new ArrayList<Attributes>();
        for (String s : attrSplit) {
            if (s.equals(NULL_ATTRIBUTE_CHAR))
                attributes.add(new Attributes());
            else
                attributes.add(new Attributes(s.replace(REPLACEMENT_CHAR, ESCAPE_CHAR)));
        }

        List<Delta> delta = new ArrayList<Delta>();
        int index = 0;
        for (String s : insertSplit) {
            if (s.startsWith(EMBED_PREFIX)) {
                // Content is media
                String embed = s.replace(EMBED_PREFIX, "").replace(REPLACEMENT_CHAR, ESCAPE_CHAR);
                JsonNode node;
                try {
                    node = mapper.readTree(embed);
                } catch (IOException exception) {
                    // Not gonna happen until logic changes
                    throw new UncheckedIOException(exception);
                }
                delta.add(new Delta(attributes.get(index), node));
            } else {
                // Content is pure string
                delta.add(new Delta(attributes.get(index), s.replace(REPLACEMENT_CHAR, ESCAPE_CHAR)));
            }
            index++;
        }

        return delta;
    }

    private static String stringfyInsert(Delta delta) {
        StringBuilder builder = new StringBuilder();

        if (delta.isEmbed()) {
            try {
                String insert = mapper.writeValueAsString(((JsonNode) delta.getInsert()));
                builder.append(insert.replace(ESCAPE_CHAR, REPLACEMENT_CHAR));
            } catch (IOException exception) {
                throw new UncheckedIOException(exception);
            }
            builder.insert(0, EMBED_PREFIX);
        } else {
            builder.append(((String) delta.getInsert()).replace(ESCAPE_CHAR, REPLACEMENT_CHAR));
        }
        builder.append(LINE_BREAK);
        return builder.toString();
    }

    private static String stringfyAttributes(Delta delta) {
        if (delta.getAttributes() == null)
            return NULL_ATTRIBUTE_CHAR + LINE_BREAK;
        else
            return delta.getAttributes().stringfy().replace(ESCAPE_CHAR, REPLACEMENT_CHAR) + LINE_BREAK;
    }

}
