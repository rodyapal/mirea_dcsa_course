// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

contract GameShop {
    address public shopOwner;

    constructor() {
        shopOwner = msg.sender;
    }

    struct GameRegistry {
        uint inStock;
        mapping (uint => string) keys;
        bool isValue;
    }

    mapping (string => GameRegistry) public games;

    function buyGame(string memory gameName) public payable returns(string memory) {
        require(games[gameName].isValue, "No such game");
        require(games[gameName].inStock > 0, "No avalibale keys");

        uint _index = --games[gameName].inStock;
        return games[gameName].keys[_index];
    }

    function refillStock(string memory gameName, string memory key) public {
        require(msg.sender == shopOwner, "Access denined");

        uint _index = games[gameName].inStock++;
        games[gameName].isValue = true;
        games[gameName].keys[_index] = key;
    }

    function withdrawAll() public {
        require(msg.sender == shopOwner, "Access denined");        
        payable(msg.sender).transfer(address(this).balance);
    }
}