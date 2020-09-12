package me.matamor.commonapi.economy.mini;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Manager
{
    private String directory;
    private String file;
    private String source;
    private LinkedList<String> lines;

    public Manager(final String directory, final String file, final boolean create) {
        this.directory = "";
        this.file = "";
        this.source = "";
        this.lines = new LinkedList<String>();
        this.directory = directory;
        this.file = file;
        if (create) {
            this.existsCreate();
        }
    }

    public String getSource() {
        return this.source;
    }

    public LinkedList<String> getLines() {
        return this.lines;
    }

    public String getDirectory() {
        return this.directory;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(final String file) {
        this.file = file;
    }

    public void setFile(final String file, final boolean create) {
        this.file = file;
        if (create) {
            this.create();
        }
    }

    public void setDirectory(final String directory) {
        this.directory = directory;
    }

    public void setDirectory(final String directory, final boolean create) {
        this.directory = directory;
        if (create) {
            this.createDirectory();
        }
    }

    private void log(final Level level, final Object message) {
        Logger.getLogger("FileManager").log(level, String.valueOf(message));
    }

    public boolean exists() {
        return this.exists(this.directory, this.file);
    }

    public boolean exists(final String file) {
        return this.exists(this.directory, file);
    }

    public boolean exists(final String directory, final String file) {
        return new File(directory, file).exists();
    }

    public void existsCreate() {
        this.existsCreate(this.directory, this.file);
    }

    public void existsCreate(final String directory, final String file) {
        if (!new File(directory).exists()) {
            if (!new File(directory, file).exists()) {
                this.create(directory, file);
            }
            else {
                this.createDirectory(directory);
            }
        }
        else if (!new File(directory, file).exists()) {
            this.create(directory, file);
        }
    }

    public boolean delete() {
        return new File(this.directory, this.file).delete();
    }

    public boolean create() {
        return this.create(this.directory, this.file);
    }

    public boolean create(final String directory, final String file) {
        try {
            new File(directory).mkdirs();
            new File(directory, file).createNewFile();
        }
        catch (IOException ex) {
            this.log(Level.SEVERE, ex);
            return false;
        }
        return true;
    }

    public boolean createDirectory() {
        return this.createDirectory(this.directory);
    }

    public boolean createDirectory(final String directory) {
        return new File(directory).mkdirs();
    }

    public boolean append(final String data) {
        return this.append(this.directory, this.file, new String[] { data });
    }

    public boolean append(final String[] lines) {
        return this.append(this.directory, this.file, lines);
    }

    public boolean append(final String file, final String data) {
        return this.append(this.directory, file, new String[] { data });
    }

    public boolean append(final String file, final String[] lines) {
        return this.append(this.directory, file, lines);
    }

    public boolean append(final String directory, final String file, final String data) {
        return this.append(directory, file, new String[] { data });
    }

    public boolean append(final String directory, final String file, final String[] lines) {
        this.existsCreate(directory, file);
        try {
            final BufferedWriter output = new BufferedWriter(new FileWriter(new File(directory, file), true));
            try {
                for (final String append : lines) {
                    output.write(append);
                    output.newLine();
                }
            }
            catch (IOException ex) {
                this.log(Level.SEVERE, ex);
                output.close();
                return false;
            }
            output.close();
            return true;
        }
        catch (FileNotFoundException ex2) {
            this.log(Level.SEVERE, ex2);
        }
        catch (IOException ex) {
            this.log(Level.SEVERE, ex);
        }
        return false;
    }

    public boolean read() {
        return this.read(this.directory, this.file);
    }

    public boolean read(final String file) {
        return this.read(this.directory, file);
    }

    public boolean read(final String directory, final String file) {
        final StringBuilder sb = new StringBuilder();
        this.lines = new LinkedList<String>();
        this.source = "";
        try {
            final BufferedReader input = new BufferedReader(new FileReader(new File(directory, file)));
            try {
                String line;
                while ((line = input.readLine()) != null) {
                    this.lines.add(line);
                    sb.append(line).append('\n');
                }
                this.source = sb.toString().trim();
                input.close();
            }
            catch (IOException ex) {
                this.log(Level.SEVERE, ex);
                return false;
            }
            return true;
        }
        catch (FileNotFoundException ex2) {
            this.log(Level.SEVERE, ex2);
            return false;
        }
    }

    public boolean write(final Object data) {
        return this.write(this.directory, this.file, new Object[] { data });
    }

    public boolean write(final Object[] lines) {
        return this.write(this.directory, this.file, lines);
    }

    public boolean write(final String file, final Object data) {
        return this.write(this.directory, file, new Object[] { data });
    }

    public boolean write(final String file, final String[] lines) {
        return this.write(this.directory, file, lines);
    }

    public boolean write(final String directory, final String file, final Object data) {
        return this.write(directory, file, new Object[] { data });
    }

    public boolean write(final String directory, final String file, final Object[] lines) {
        this.existsCreate(directory, file);
        try {
            final BufferedWriter output = new BufferedWriter(new FileWriter(new File(directory, file)));
            try {
                for (final Object line : lines) {
                    output.write(String.valueOf(line));
                    output.newLine();
                }
                output.close();
            }
            catch (IOException ex) {
                this.log(Level.SEVERE, ex);
                output.close();
                return false;
            }
            return true;
        }
        catch (FileNotFoundException ex2) {
            this.log(Level.SEVERE, ex2);
        }
        catch (IOException ex) {
            this.log(Level.SEVERE, ex);
        }
        return false;
    }

    public boolean remove(final Object line) {
        return this.remove(this.directory, this.file, new Object[] { line });
    }

    public boolean remove(final Object[] lines) {
        return this.remove(this.directory, this.file, lines);
    }

    public boolean remove(final String file, final Object[] lines) {
        return this.remove(this.directory, file, lines);
    }

    public boolean remove(final String directory, final String file, final Object line) {
        return this.remove(directory, file, new Object[] { line });
    }

    public boolean remove(final String directory, final String file, final Object[] lines) {
        this.existsCreate(directory, file);
        this.read(directory, file);
        final File input = new File(directory, file);
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(input));
            try {
                for (final String current : this.lines) {
                    boolean found = false;
                    for (final Object line : lines) {
                        if (current.equals(String.valueOf(line))) {
                            found = true;
                        }
                    }
                    if (!found) {
                        writer.write(current);
                        writer.newLine();
                    }
                }
                writer.close();
            }
            catch (IOException e) {
                writer.close();
                return false;
            }
        }
        catch (Exception e2) {
            return false;
        }
        return true;
    }

    public void removeDuplicates() {
        this.removeDupilcates(this.directory, this.file);
    }

    public void removeDuplicates(final String file) {
        this.removeDupilcates(this.directory, file);
    }

    public void removeDupilcates(final String directory, final String file) {
        final Set<String> uniqueLines = new LinkedHashSet<String>();
        this.existsCreate(directory, file);
        final File input = new File(directory, file);
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(input));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    uniqueLines.add(line);
                }
                reader.close();
            }
            catch (IOException e2) {
                reader.close();
                return;
            }
            final BufferedWriter writer = new BufferedWriter(new FileWriter(input));
            try {
                for (final String current : uniqueLines) {
                    writer.write(current);
                    writer.newLine();
                }
                writer.close();
            }
            catch (IOException e2) {
                writer.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}