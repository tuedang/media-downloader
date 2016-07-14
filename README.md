# media-downloader

Download audio files in multiple sites, until now it supports to download from these sites:
- Nhac.vui.vn
- Nhaccuatui.com
- Mp3.zing.vn
- Chiasenhac.com

Application download multiple file then create a playlist in m3u format.

By default, the downloaded file name will be very long, the application correct and make it readable.
Beside that, all audio files will be tagged in fields: song title, track number, total track number, album, artist.

# build
System requirement: Java 8

- In order run as application (click and run): mvn package -P app
- To build the war file to run on tomcat or jetty: mvn package -P web
