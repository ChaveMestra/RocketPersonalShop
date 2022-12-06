package com.rocketmc.personalshops;

import com.rocketmc.personalshops.bank.BankListener;
import com.rocketmc.personalshops.bank.BankManager;
import com.rocketmc.personalshops.command.BankCommand;
import com.rocketmc.personalshops.command.ShopCommand;
import com.rocketmc.personalshops.config.Config;
import com.rocketmc.personalshops.database.DatabaseManager;
import com.rocketmc.personalshops.database.MySQL;
import com.rocketmc.personalshops.gui.GUIListener;
import com.rocketmc.personalshops.gui.GUIManager;
import com.rocketmc.personalshops.gui.button.ButtonListener;
import com.rocketmc.personalshops.gui.button.ButtonManager;
import com.rocketmc.personalshops.player.ShopPlayerListener;
import com.rocketmc.personalshops.player.ShopPlayerManager;
import com.rocketmc.personalshops.prompt.PromptListener;
import com.rocketmc.personalshops.prompt.PromptManager;
import com.rocketmc.personalshops.shop.ShopListener;
import com.rocketmc.personalshops.shop.ShopManager;
import com.rocketmc.personalshops.shop.products.ProductManager;
import com.rocketmc.personalshops.util.Debug;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public class PersonalShops extends JavaPlugin {

    @Getter
    public static PersonalShops instance;
    public Config configuration;
    public ShopPlayerManager shopPlayerManager;
    public ShopManager shopManager;
    public ProductManager productManager;
    public PromptManager promptManager;
    public MySQL mySQL;
    public DatabaseManager databaseManager;
    public GUIManager guiManager;
    public ButtonManager buttonManager;
    public BankManager bankManager;
    public HikariDataSource hikari;
    public Economy economy;

    @Override
    public void onEnable() {
        instance = this;
        setConfiguration(new Config());
        configuration.setup(this);
        setupEconomy();
        initializeDatabase();
        initializeManagers();
        registerCommands();
        registerListeners();
        loadData();
    }

    @Override
    public void onDisable() {
        this.shopPlayerManager.getShopPlayerList().stream().filter(shopPlayer -> shopPlayer.getShop() != null).forEach(shopPlayer -> shopPlayer.getShop().removeShop());
    }

    private void registerCommands() {
        getCommand("personalshops").setExecutor(new ShopCommand());
        getCommand("banco").setExecutor(new BankCommand());
    }

    public void loadData() {
        //tudo q for loadar pra ram qnd o sv iniciar bota aki
        //  this.databaseManager.loadShopPlayers();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ShopListener(), this);
        Bukkit.getPluginManager().registerEvents(new ShopPlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new PromptListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new ButtonListener(), this);
        Bukkit.getPluginManager().registerEvents(new BankListener(), this);

    }

    private void initializeManagers() {
        Debug.print("Inicializando managers");
        setShopPlayerManager(new ShopPlayerManager());
        setShopManager(new ShopManager());
        setProductManager(new ProductManager());
        setPromptManager(new PromptManager());
        setDatabaseManager(new DatabaseManager());
        setGuiManager(new GUIManager());
        setButtonManager(new ButtonManager());
        setBankManager(new BankManager());
        Debug.print("Managers inicializados");
    }

    private void initializeDatabase() {
        Debug.print("Inicializando database");
        setMySQL(new MySQL());
        setHikari(new HikariDataSource());
        mySQL.initializeConnectionPool();
        Debug.print("Database inicializada");

    }

    private void setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            economy = rsp.getProvider();
        }
    }

}
