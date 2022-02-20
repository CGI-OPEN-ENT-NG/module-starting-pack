interface IViewModel {
    test: string;
}

class ViewModel implements IViewModel {
    test: string;

    constructor(scope) {
       this.test = "coucou je suis un sniplet";
       console.log("scope angular: ", scope);
    }
}

export const exampleSniplet = {
    title: 'example.content',
    public: false,
    that: null,
    controller: {
        init: async function (): Promise<void> {
            this.vm = new ViewModel(this);
        },
    }

};