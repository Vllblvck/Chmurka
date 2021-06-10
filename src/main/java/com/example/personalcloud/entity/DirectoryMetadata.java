package com.example.personalcloud.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "directory_metadata")
public class DirectoryMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "dir_name")
    @NotNull
    private String dirName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<FileMetadata> fileMetadataList;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private DirectoryMetadata parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DirectoryMetadata> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public List<FileMetadata> getFileMetadataList() {
        return fileMetadataList;
    }

    public void setFileMetadataList(List<FileMetadata> fileMetadataList) {
        this.fileMetadataList = fileMetadataList;
    }

    public DirectoryMetadata getParent() {
        return parent;
    }

    public void setParent(DirectoryMetadata parent) {
        this.parent = parent;
    }

    public List<DirectoryMetadata> getChildren() {
        return children;
    }

    public void setChildren(List<DirectoryMetadata> children) {
        this.children = children;
    }

    public long getParentId() {
        return this.parent == null ? 0 : this.parent.getId();
    }
}