import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = `${environment.apiUrl}/api/users`;

  constructor(private http: HttpClient) { }

  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/id/${userId}`);
  }

  getUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/username/${username}`);
  }

  updateProfile(userId: number, user: User): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/settings/${userId}`, user);
  }

  updatePassword(userId: number, user: User): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${userId}/password`, user);
  }

  deleteUser(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${userId}`);
  }

  followUser(followerId: number, followedId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${followerId}/follow/${followedId}`, {});
  }

  getLikedPostsByUser(userId: number): Observable<any[]> {  // Update with post model
    return this.http.get<any[]>(`${this.apiUrl}/${userId}/likedPosts`);
  }

  getCommentsByUser(userId: number): Observable<any[]> {  // Update with comment model
    return this.http.get<any[]>(`${this.apiUrl}/${userId}/comments`);
  }
}
