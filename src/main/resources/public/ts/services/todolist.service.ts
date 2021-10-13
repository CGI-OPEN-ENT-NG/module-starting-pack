import {ng} from 'entcore'
import http, {AxiosResponse} from 'axios';


export interface ITodolistService {
    test(): Promise<AxiosResponse>;
}

export const todolistService: ITodolistService = {
    // won't work since example
    test: async (): Promise<AxiosResponse> => {
        return http.get(`/todoapp/test/ok`);
    }
};

export const TodolistService = ng.service('TodolistService', (): ITodolistService => todolistService);