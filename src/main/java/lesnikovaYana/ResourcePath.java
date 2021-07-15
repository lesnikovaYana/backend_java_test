package ru.lesnikovaYana;

public enum ResourcePath {
    FILE_1BITE("src/test/resources/file_1bite.jpeg"),
    FILE_10MB("src/test/resources/file_10mb.jpeg"),
    FILE_TIFF("src/test/resources/file_TIFF_1MB.tiff"),
    FILE_OGG("src/test/resources/file_OGG_480_1_7mg.ogg"),
    FILE_CHANGED_EXE("src/test/resources/file_changed_exe.jpg"),
    FILE_DOCX("src/test/resources/file_docx.docx"),
    FILE_EMPTY("/path/to/file"),
    FILE_JPG("src/test/resources/file_JPG_500kb.jpg"),
    FILE_GIF("src/test/resources/file_gif.gif"),
    FILE_PNG("src/test/resources/file_png.png"),
    FILE_BMP("src/test/resources/file_bmp.bmp"),
    FILE_MP4("src/test/resources/file_mp4.mp4"),
    FILE_AVI("src/test/resources/file_AVI_480_750kB.avi"),
    FILE_MOV("src/test/resources/file_MOV_480_700kB.mov"),
    FILE_WEBM("src/test/resources/file_WEBM_480_900KB.webm"),
    FILE_HD("src/test/resources/file_HD.jpg"),
    FILE_1X1PX("src/test/resources/file_1x1px.jpg"),
    TEST_URL("https://images.kinorium.com/movie/shot/37317/w1500_127038.jpg");

    private String title;

    ResourcePath(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ResourcePath{" +
                "title='" + title + '\'' +
                '}';
    }
}
