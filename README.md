# media-downloader

Download audio album in multiple sites, until now 

**It supports to download from these sites:**

- Nhac.vui.vn
- Nhaccuatui.com
- Mp3.zing.vn
- Chiasenhac.vn (download flac only - ignore if there's no flac file in album)

**Generate playlist**

Application download multiple file then create a playlist in m3u format.

**Tagging**

By default, the downloaded file name will be very long, the application correct and make it readable.
Beside that, all audio files will be tagged in fields: song title, track number, total track number, album, artist.

# build
- System requirement:
    - java 8
    - maven

- In order run as application (click and run): `mvn package -P app`
- Build war file to run on tomcat or jetty: `mvn package -P web`
