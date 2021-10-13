module.exports = {
    "transform": {
        ".(ts|tsx)": "<rootDir>/node_modules/ts-jest/preprocessor.js"
    },
    "testRegex": "(/__tests__/.*|\\.(test|spec))\\.(ts|tsx|js)$",
    "moduleFileExtensions": [
        "ts",
        "tsx",
        "js"
    ],
    "testPathIgnorePatterns": [
        "/node_modules/",
        "<rootDir>/module-starting-pack/build/",
        "<rootDir>/module-starting-pack/out/"
    ],
    "verbose": true,
    "testURL": "http://localhost/",
    "coverageDirectory": "coverage/front",
    "coverageReporters": [
        "text",
        "cobertura"
    ]
};
