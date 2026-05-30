package com.ms.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ms.admin.entity.*;
import com.ms.admin.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AdminService {

    private final SongMapper songMapper;
    private final ArtistMapper artistMapper;
    private final AlbumMapper albumMapper;
    private final PlaylistMapper playlistMapper;
    private final UserMapper userMapper;
    private final FavoriteMapper favoriteMapper;
    private final PlaylistSongMapper playlistSongMapper;

    public AdminService(SongMapper songMapper, ArtistMapper artistMapper, AlbumMapper albumMapper,
                        PlaylistMapper playlistMapper, UserMapper userMapper, FavoriteMapper favoriteMapper,
                        PlaylistSongMapper playlistSongMapper) {
        this.songMapper = songMapper;
        this.artistMapper = artistMapper;
        this.albumMapper = albumMapper;
        this.playlistMapper = playlistMapper;
        this.userMapper = userMapper;
        this.favoriteMapper = favoriteMapper;
        this.playlistSongMapper = playlistSongMapper;
    }

    // ====== Songs ======
    public List<Song> getAllSongs() { return songMapper.selectAllWithDetails(); }
    public Song getSongById(Integer id) { return songMapper.selectByIdWithDetails(id); }

    @Transactional
    public Song createSong(Song song) {
        song.setCreatedAt(LocalDateTime.now());
        songMapper.insert(song);
        return song;
    }

    @Transactional
    public Song updateSong(Integer id, Song song) {
        Song existing = songMapper.selectById(id);
        if (existing == null) return null;
        if (song.getName() != null) existing.setName(song.getName());
        if (song.getArtistId() != null) existing.setArtistId(song.getArtistId());
        if (song.getAlbumId() != null) existing.setAlbumId(song.getAlbumId());
        if (song.getUrl() != null) existing.setUrl(song.getUrl());
        if (song.getDurationText() != null) existing.setDurationText(song.getDurationText());
        if (song.getLyrics() != null) existing.setLyrics(song.getLyrics());
        if (song.getSortOrder() != null) existing.setSortOrder(song.getSortOrder());
        songMapper.updateById(existing);
        return existing;
    }

    @Transactional
    public void deleteSong(Integer id) { songMapper.deleteById(id); }
    public Long getSongCount() { return songMapper.selectCount(null); }

    // ====== Artists ======
    public List<Map<String, Object>> getAllArtists() { return artistMapper.selectAllWithCount(); }

    @Transactional
    public Artist createArtist(Artist artist) {
        artist.setCreatedAt(LocalDateTime.now());
        artistMapper.insert(artist);
        return artist;
    }

    @Transactional
    public void deleteArtist(Integer id) { artistMapper.deleteById(id); }
    public Long getArtistCount() { return artistMapper.selectCount(null); }

    // ====== Albums ======
    public List<Map<String, Object>> getAllAlbums() { return albumMapper.selectAllWithDetails(); }

    @Transactional
    public Album createAlbum(Album album) {
        album.setCreatedAt(LocalDateTime.now());
        albumMapper.insert(album);
        return album;
    }

    @Transactional
    public void deleteAlbum(Integer id) { albumMapper.deleteById(id); }
    public Long getAlbumCount() { return albumMapper.selectCount(null); }

    // ====== Playlists ======
    public List<Map<String, Object>> getAllPlaylists() { return playlistMapper.selectByUserIdWithCount(1); }
    public Long getPlaylistCount() { return playlistMapper.selectCount(null); }

    // ====== Stats ======
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("songs", getSongCount());
        stats.put("artists", getArtistCount());
        stats.put("albums", getAlbumCount());
        stats.put("playlists", getPlaylistCount());
        return stats;
    }
}
