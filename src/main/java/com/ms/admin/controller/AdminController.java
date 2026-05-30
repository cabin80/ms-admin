package com.ms.admin.controller;

import com.ms.admin.config.Result;
import com.ms.admin.entity.*;
import com.ms.admin.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Stats
    @GetMapping("/stats")
    public Map<String, Object> stats() {
        return Result.success(adminService.getStats());
    }

    // Songs
    @GetMapping("/songs")
    public Map<String, Object> getSongs() {
        return Result.success(adminService.getAllSongs());
    }

    @GetMapping("/songs/{id}")
    public Map<String, Object> getSong(@PathVariable Integer id) {
        Song s = adminService.getSongById(id);
        if (s == null) return Result.error(404, "Not found");
        return Result.success(s);
    }

    @PostMapping("/songs")
    public Map<String, Object> createSong(@RequestBody Song song) {
        Song created = adminService.createSong(song);
        return Result.success(Map.of("id", created.getId()), "创建成功");
    }

    @PutMapping("/songs/{id}")
    public Map<String, Object> updateSong(@PathVariable Integer id, @RequestBody Song song) {
        Song updated = adminService.updateSong(id, song);
        if (updated == null) return Result.error(404, "Not found");
        return Result.success(null, "更新成功");
    }

    @DeleteMapping("/songs/{id}")
    public Map<String, Object> deleteSong(@PathVariable Integer id) {
        adminService.deleteSong(id);
        return Result.success(null, "删除成功");
    }

    // Artists
    @GetMapping("/artists")
    public Map<String, Object> getArtists() {
        return Result.success(adminService.getAllArtists());
    }

    @PostMapping("/artists")
    public Map<String, Object> createArtist(@RequestBody Artist artist) {
        Artist created = adminService.createArtist(artist);
        return Result.success(Map.of("id", created.getId()), "创建成功");
    }

    @DeleteMapping("/artists/{id}")
    public Map<String, Object> deleteArtist(@PathVariable Integer id) {
        adminService.deleteArtist(id);
        return Result.success(null, "删除成功");
    }

    // Albums
    @GetMapping("/albums")
    public Map<String, Object> getAlbums() {
        return Result.success(adminService.getAllAlbums());
    }

    @PostMapping("/albums")
    public Map<String, Object> createAlbum(@RequestBody Album album) {
        Album created = adminService.createAlbum(album);
        return Result.success(Map.of("id", created.getId()), "创建成功");
    }

    @DeleteMapping("/albums/{id}")
    public Map<String, Object> deleteAlbum(@PathVariable Integer id) {
        adminService.deleteAlbum(id);
        return Result.success(null, "删除成功");
    }

    // Playlists
    @GetMapping("/playlists")
    public Map<String, Object> getPlaylists() {
        return Result.success(adminService.getAllPlaylists());
    }
}
