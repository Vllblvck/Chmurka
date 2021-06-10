package com.example.personalcloud.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;


@Entity
@Table(name = "file_metadata")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "file_name")
    @NotNull
    private String fileName;

    @Column(name = "size")
    @NotNull
    private long size;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private DirectoryMetadata parent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public DirectoryMetadata getParent() {
        return parent;
    }

    public void setParent(DirectoryMetadata parent) {
        this.parent = parent;
    }

    public long getParentId() {
        return this.parent == null ? 0 : this.parent.getId();
    }
}
