import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import {todolistService} from '../todolist.service';

describe('TodolistService', () => {
    it('returns data when retrieve request is correctly called', done => {
        const mock = new MockAdapter(axios);
        const data = {response: true};
        mock.onGet(`/todoapp/test/ok`).reply(200, data);
        todolistService.test().then(response => {
            expect(response.data).toEqual(data);
            done();
        });
    });

    it('returns data when retrieve request is correctly called other method', done => {
        let spy = jest.spyOn(axios, "get");
        todolistService.test().then(response => {
            expect(spy).toHaveBeenCalledWith("/todoapp/test/ok");
            done();
        });
    });
});
