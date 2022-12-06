package com.rocketmc.personalshops.shop;


import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.rocketmc.personalshops.PersonalShops;
import com.rocketmc.personalshops.gui.button.Button;
import com.rocketmc.personalshops.gui.button.types.ShopProductButton;
import com.rocketmc.personalshops.gui.types.ShopGUI;
import com.rocketmc.personalshops.player.ShopPlayer;
import com.rocketmc.personalshops.shop.products.Product;
import com.rocketmc.personalshops.util.ChestUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.rocketmc.personalshops.util.StringUtil.decimalFormat;
import static com.rocketmc.personalshops.util.StringUtil.f;

@Getter
@Setter
public class Shop {

    public Block block;

    public ShopPlayer shopPlayer;
    public String title;
    public ShopStatus shopStatus = ShopStatus.CLOSED;
    public List<Product> productList;
    public long createdOnMillis;
    public Location chest1;
    public Location chest2;
    public Hologram hologram;
    public boolean hasEffect;
    public boolean removalBypass;
    public int removalCooldown;
    public int slots;
    public int secondsWithoutProduct;


    public Shop(ShopPlayer shopPlayer, Block block) {
        this.shopPlayer = shopPlayer;
        this.block = block;
        this.title = "Loja de " + shopPlayer.getPlayerName();
        productList = new ArrayList<Product>();
        createdOnMillis = System.currentTimeMillis();
        hasEffect = shopPlayer.getPlayer().hasPermission("personalshops.effect");
        removalBypass = shopPlayer.getPlayer().hasPermission("personalshops.removebypass");
        this.setRemovalCooldown(shopPlayer.getShopPermissionCooldown());
        setSlots(shopPlayer.getShopSlots());
        setSecondsWithoutProduct(0);

    }

    public void placeShop() {
        Location chestLocation = getBlock().getLocation().clone().add(0, 1, 0);
        ChestUtil.placeFacedChest(this, chestLocation);
        createShopHologram(chestLocation.clone().add(0, 2.28, 0.5));
    }

    public void changeTitle(String newTitle) {
        if (shopPlayer.getPlayer().hasPermission("personalshops.titlecolor")) {
            this.title = f(newTitle);
        } else {
            this.title = newTitle;
        }
        updateHologram();
    }


    //pra ver se ja expirou o tempo da loja
    public boolean expired() {
        return createdOnMillis / 1000 + this.getRemovalCooldown() <= System.currentTimeMillis() / 1000;
    }

    public void switchStatus() {
        if (this.shopStatus == ShopStatus.CLOSED) {
            if (hasProducts())
                setShopStatus(ShopStatus.OPEN);
        } else {
            setShopStatus(ShopStatus.CLOSED);
        }
        updateHologram();
    }

    public boolean hasProducts() {
        if (this.productList.size() > 0) return true;
        setShopStatus(ShopStatus.CLOSED);
        updateHologram();
        if (shopPlayer.getPlayer() != null) {
            shopPlayer.getPlayer().sendMessage(f("&7Sua lojinha nao possui itens a venda por isso fechou."));
            shopPlayer.getPlayer().sendMessage(f("&7Adquira &aVIP&7 para nao ter sua loja removida ao vender tudo"));
        }
        return false;
    }

    public void updateHologram() {
        hologram.clearLines();
        String status = shopStatus == ShopStatus.CLOSED ? f("&7[&cFECHADO&7]") : f("&7[&aABERTO&7]");
        if (shopPlayer.getPlayer().hasPermission("personalshops.itemhologram")) {
            hologram.appendItemLine(new ItemStack(Material.EMERALD, 1));
        }
        if (shopPlayer.getPlayer().hasPermission("personalshops.titlecolor")) {
            hologram.appendTextLine(f(title));
        } else {
            hologram.appendTextLine(title);
        }
        hologram.appendTextLine(status);
    }

    public void createShopHologram(Location location) {
        hologram = HologramsAPI.createHologram(PersonalShops.getInstance(), location);
        updateHologram();
    }

    public void open(ShopPlayer shopPlayer) {
        if (this.getShopStatus() != ShopStatus.OPEN) {
            if (shopPlayer.getShop() == null || !shopPlayer.getShop().equals(this)) {
                return;
            }
        }
        PersonalShops.getInstance().getGuiManager().startGUI(shopPlayer, new ShopGUI(this, shopPlayer));
    }

    public void registerProduct(Product product) {
        shopPlayer.getPlayer().getInventory().removeItem(product.getItemStack());
        this.getProductList().add(product);
        PersonalShops.getInstance().getGuiManager().refreshGUIs();
        shopPlayer.getPlayer().sendMessage(f("&aProduto registrado com sucesso! Preco/unidade: $" + decimalFormat.format(product.getPrice())));
        shopPlayer.getShop().open(shopPlayer);

    }

    public void unregisterProduct(Product product) {
        System.out.println("desregistrando produto, tamanho da lista antes: " + this.getProductList().size());
        this.getProductList().remove(product);
        System.out.println("oh, to desregistrando o produto " + product.getItemStack() + " do shop do " + product.getShop().getShopPlayer().getPlayerName());
        System.out.println("lista depois da remocao:");
        for (Product product1 : this.getProductList()) {
            System.out.println("oh, ainda tem o produto " + product.getItemStack() + " registrado! eh similar ao removido? " + product.getItemStack().isSimilar(product1.itemStack));
        }
        System.out.println("no fim, tamanho da lista: " + this.getProductList().size());
        hasProducts();
        PersonalShops.getInstance().getGuiManager().refreshGUIs();
        shopPlayer.getPlayer().getInventory().addItem(product.getItemStack());
    }

    public void unregisterAllProducts() {
        for (Product product : getProductList()) {
            shopPlayer.getBank().getBinContent().add(product.itemStack);
        }
        getProductList().clear();
        PersonalShops.getInstance().getGuiManager().refreshGUIs();
    }

    public void sellProduct(Product product, int quantity, ShopPlayer buyer) {
        if (!PersonalShops.getInstance().getEconomy().has(buyer.getPlayer(), product.getPrice() * quantity)) {
            buyer.getPlayer().sendMessage(f("&cSaldo insuficiente para essa compra!"));
            return;
        }


        if (quantity >= product.getItemStack().getAmount()) {
            quantity = product.getItemStack().getAmount();
            product.getShop().getProductList().remove(product);
            if (!hasProducts() && !isRemovalBypass()) {
                removeShop();
            }
        } else {
            product.getItemStack().setAmount(product.itemStack.getAmount() - quantity);
        }
        PersonalShops.getInstance().getEconomy().withdrawPlayer(buyer.getPlayer(), product.getPrice() * quantity);
        PersonalShops.getInstance().getGuiManager().refreshGUIs();
        ItemStack toReceive = product.getItemStack().clone();
        toReceive.setAmount(quantity);
        buyer.getPlayer().getPlayer().getInventory().addItem(toReceive);
        buyer.getPlayer().sendMessage(f("&aVoce comprou x" + quantity + " do item por $" + product.getRoundedPrice(quantity) + " da lojinha do " + product.getShop().getShopPlayer().getPlayerName() + "!"));

        PersonalShops.getInstance().getEconomy().depositPlayer(product.getShop().getShopPlayer().getPlayerName(), product.getPrice() * quantity);

        if (product.getShop().getShopPlayer().getPlayer() != null) {

            product.getShop().getShopPlayer().getPlayer().sendMessage(f("&aVoce vendeu x" + quantity + " do item " + product.getItemStack().getType().toString() + " por $" + product.getRoundedPrice(quantity) + " na sua lojinha!"));
        }

        //product.getShop().open(shopPlayer);
    }

    public void removeShop() {
        unregisterAllProducts();
        hologram.delete();
        chest1.getBlock().setType(Material.AIR);
        chest2.getBlock().setType(Material.AIR);
        PersonalShops.getInstance().getDatabaseManager().saveShopPlayer(shopPlayer);
        shopPlayer.setShop(null);
        if (shopPlayer.getPlayer() != null) {
            shopPlayer.getPlayer().sendMessage(f("&aSua lojinha foi removida!"));
        }
    }

    public void expireShop() {
        removeShop();
        if (shopPlayer.getPlayer() != null) {
            shopPlayer.getPlayer().sendMessage(f("&f&lPORQUE? &7Sua lojinha atingiu o &climite de horas&7."));
            shopPlayer.getPlayer().sendMessage(f("&7Seja &aVIP&7 e tenha mais horas. Voce ja pode criar outra."));
            shopPlayer.getPlayer().sendMessage(f("&7Os itens da loja foram enviados para o &eDeposito"));
        }
    }

    public List<Button> getShopProductsButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        for (Product product : this.productList) {
            buttons.add(new ShopProductButton(product));
        }
        return buttons;
    }

    public enum ShopStatus {
        OPEN, CLOSED
    }

}
