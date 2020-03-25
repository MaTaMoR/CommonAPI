package me.matamor.commonapi.modules;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.matamor.commonapi.modules.exception.ModuleException;
import org.bukkit.plugin.PluginAwareness;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

@RequiredArgsConstructor
public class ModuleDescription {

    private static final ThreadLocal<Yaml> YAML = new ThreadLocal<Yaml>() {
        @Override
        protected Yaml initialValue() {
            return new Yaml(new SafeConstructor() {
                {
                    yamlConstructors.put(null, new AbstractConstruct() {
                        @Override
                        public Object construct(final Node node) {
                            if (!node.getTag().startsWith("!@")) {
                                // Unknown tag - will fail
                                return SafeConstructor.undefinedConstructor.construct(node);
                            }
                            // Unknown awareness - provide a graceful substitution
                            return new PluginAwareness() {
                                @Override
                                public String toString() {
                                    return node.toString();
                                }
                            };
                        }
                    });
                    for (final PluginAwareness.Flags flag : PluginAwareness.Flags.values()) {
                        yamlConstructors.put(new Tag("!@" + flag.name()), new AbstractConstruct() {
                            @Override
                            public PluginAwareness.Flags construct(final Node node) {
                                return flag;
                            }
                        });
                    }
                }
            });
        }
    };

    @Getter
    private String mainClass;

    @Getter
    private String name;

    @Getter
    private String version;

    public ModuleDescription(final InputStream stream) {
        loadMap(asMap(YAML.get().load(stream)));
    }

    public ModuleDescription(final Reader reader) {
        loadMap(asMap(YAML.get().load(reader)));
    }

    private void loadMap(Map<?, ?> map) {
        try {
            this.name = map.get("name").toString();

            if (!this.name.matches("^[A-Za-z0-9 _.-]+$")) {
                throw new ModuleException("name '" + name + "' contains invalid characters.");
            }

            this.name = this.name.replace(' ', '_');
        } catch (NullPointerException e) {
            throw new ModuleException("name is not defined", e);
        } catch (ClassCastException e) {
            throw new ModuleException("name is of wrong type", e);
        }

        try {
            this.version = map.get("version").toString();
        } catch (NullPointerException e) {
            throw new ModuleException("version is not defined", e);
        } catch (ClassCastException e) {
            throw new ModuleException("version is of wrong type", e);
        }

        try {
            this.mainClass = map.get("main").toString();
            if (this.mainClass.startsWith("org.bukkit.")) {
                throw new ModuleException("main may not be within the org.bukkit namespace");
            }
        } catch (NullPointerException e) {
            throw new ModuleException("main is not defined", e);
        } catch (ClassCastException e) {
            throw new ModuleException("main is of wrong type", e);
        }
    }

    private Map<?,?> asMap(Object object) {
        if (object instanceof Map) {
            return (Map<?,?>) object;
        }

        throw new RuntimeException(object + " is not properly structured.");
    }
}
