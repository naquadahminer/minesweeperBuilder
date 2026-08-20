package com.example.minesweeperbuilder;

public interface OnFieldActionListener {
    void onDeleteClick(SavedField field);
    void onPlayClick(SavedField field);
    void onUploadClick(SavedField field);
    void onRenameClick(SavedField field);
    void onCloneClick(SavedField field);
}
