package com.example.personalcloud.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;


@Entity
@Table(name = "file_metadata", indexes = {
        @Index(columnList = "file_name", name = "file_name_idx")
})
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

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void setSize(Long size) {
        this.size = size;
    }
}
