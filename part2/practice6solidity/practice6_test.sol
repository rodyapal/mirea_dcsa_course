// SPDX-License-Identifier: GPL-3.0
        
pragma solidity >=0.4.22 <0.9.0;

// This import is automatically injected by Remix
import "remix_tests.sol"; 

// This import is required to use custom transaction context
// Although it may fail compilation in 'Solidity Compiler' plugin
// But it will work fine in 'Solidity Unit Testing' plugin
import "remix_accounts.sol";
import "../contracts/Practice6.sol";

contract GameShopTest {
    GameShop gameShop;
    
    function beforeAll() public {
        gameShop = new GameShop();
    }

    function checkShopOwner() public {
        address owner = gameShop.shopOwner();
        Assert.equal(owner, address(this), "The owner should be the current test contract");
    }

    function testRefillStock() public {
        string memory gameName = "Game1";
        string memory key = "Key1";

        gameShop.refillStock(gameName, key);

        uint inStock;
        bool isValue;
        (inStock, isValue) = gameShop.games(gameName);
        Assert.equal(inStock, 1, "Stock was not refilled");
        Assert.ok(isValue, "isValue was no set");
    }

    function testBuyGame() public {
        string memory gameName = "Game2";
        string memory key = "Key2";

        gameShop.refillStock(gameName, key);

        string memory keyFromShop = gameShop.buyGame(gameName);
        Assert.equal(keyFromShop, "Key2", "Invalid key from shop");

        uint inStock;
        (inStock, ) = gameShop.games(gameName);
        Assert.equal(inStock, 0, "Stock was not decreased");
    }
}