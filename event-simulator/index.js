const axios = require("axios");

const API_URL = "http://localhost:8080/api/events";
const NUM_USERS = 15;

const EVENTS = {
  PAGE_VIEW: "PAGE_VIEW",
  PRODUCT_VIEW: "PRODUCT_VIEW",
  ADD_TO_CART: "ADD_TO_CART",
  REMOVE_FROM_CART: "REMOVE_FROM_CART",
  CHECKOUT_START: "CHECKOUT_START",
  PURCHASE: "PURCHASE",
  SEARCH: "SEARCH",
  SESSION_START: "SESSION_START",
};

const DEVICE_TYPES = [
  "DESKTOP",
  "DESKTOP",
  "DESKTOP",
  "IPHONE",
  "IPHONE",
  "ANDROID",
];

const PAGES = ["/home", "/products", "/about", "/deals", "/new-arrivals"];

const SEARCH_TERMS = [
  "running shoes",
  "shorts",
  "jacket",
  "gym bag",
  "yoga mat",
  "socks",
  "hat",
  "leggings",
];

const PRODUCTS = [
  {
    productId: "001",
    productName: "Phantom Run Shoes",
    price: 134.99,
    category: "Footwear",
  },
  {
    productId: "002",
    productName: "Aero Stride Trainers",
    price: 119.99,
    category: "Footwear",
  },
  {
    productId: "003",
    productName: "Trail Grip Hikers",
    price: 149.99,
    category: "Footwear",
  },
  {
    productId: "004",
    productName: "Velocity Track Spikes",
    price: 89.99,
    category: "Footwear",
  },
  {
    productId: "005",
    productName: "FlexKnit Compression Tee",
    price: 44.99,
    category: "Tops",
  },
  {
    productId: "006",
    productName: "DryCore Tank Top",
    price: 34.99,
    category: "Tops",
  },
  {
    productId: "007",
    productName: "Summit Half-Zip Pullover",
    price: 74.99,
    category: "Tops",
  },
  {
    productId: "008",
    productName: "ThermoShield Windbreaker",
    price: 109.99,
    category: "Outerwear",
  },
  {
    productId: "009",
    productName: "StormLite Rain Jacket",
    price: 129.99,
    category: "Outerwear",
  },
  {
    productId: "010",
    productName: "Apex Down Vest",
    price: 94.99,
    category: "Outerwear",
  },
  {
    productId: "011",
    productName: "PowerFlex Training Shorts",
    price: 49.99,
    category: "Bottoms",
  },
  {
    productId: "012",
    productName: "EnduroFit Joggers",
    price: 64.99,
    category: "Bottoms",
  },
  {
    productId: "013",
    productName: "ProStride Leggings",
    price: 59.99,
    category: "Bottoms",
  },
  {
    productId: "014",
    productName: "IronGrip Lifting Gloves",
    price: 29.99,
    category: "Accessories",
  },
  {
    productId: "015",
    productName: "PulseBand Heart Rate Monitor",
    price: 79.99,
    category: "Accessories",
  },
  {
    productId: "016",
    productName: "HydroFlask Sport Bottle",
    price: 24.99,
    category: "Accessories",
  },
  {
    productId: "017",
    productName: "TitanFit Gym Bag",
    price: 54.99,
    category: "Accessories",
  },
  {
    productId: "018",
    productName: "QuickWrap Resistance Bands",
    price: 19.99,
    category: "Equipment",
  },
  {
    productId: "019",
    productName: "CoreBalance Yoga Mat",
    price: 39.99,
    category: "Equipment",
  },
  {
    productId: "020",
    productName: "PowerRoll Foam Roller",
    price: 34.99,
    category: "Equipment",
  },
];

// --- Utility functions ---

function getUUID() {
  return crypto.randomUUID();
}

function random(max) {
  return Math.floor(Math.random() * max);
}

function pick(arr) {
  return arr[random(arr.length)];
}

function chance(percent) {
  return Math.random() * 100 < percent;
}

function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

// --- Send event ---

async function sendEvent(userId, sessionId, eventType, meta = {}) {
  try {
    const body = {
      userId,
      sessionId,
      eventType,
      timestamp: new Date().toISOString(),
      metadata: meta,
    };
    await axios.post(API_URL, body);
    console.log(`  [${eventType}] ${meta.productName || meta.page || ""}`);
  } catch (err) {
    console.error(`  [${eventType}] FAILED — ${err.message}`);
  }
}

// --- Simulate one user session ---

async function simulateUser(userNum) {
  const userId = `usr_${getUUID().slice(0, 8)}`;
  const sessionId = `sess_${getUUID().slice(0, 8)}`;
  const deviceType = pick(DEVICE_TYPES);
  const baseMeta = { deviceType };
  const counts = {};

  async function track(userId, sessionId, eventType, meta = {}) {
    counts[eventType] = (counts[eventType] || 0) + 1;
    await sendEvent(userId, sessionId, eventType, meta);
  }

  console.log(`\nUser ${userNum} (${userId}) — ${deviceType}`);

  await track(userId, sessionId, EVENTS.SESSION_START, baseMeta);
  await sleep(100);

  const numPages = 1 + random(3);
  for (let p = 0; p < numPages; p++) {
    const page = pick(PAGES);
    await track(userId, sessionId, EVENTS.PAGE_VIEW, { ...baseMeta, page });
    await sleep(100);
  }

  if (chance(60)) {
    const searchTerm = pick(SEARCH_TERMS);
    await track(userId, sessionId, EVENTS.SEARCH, {
      ...baseMeta,
      page: "/search",
      searchTerm,
    });
    await sleep(100);
  }

  const numProductViews = 1 + random(5);
  const cart = [];

  for (let v = 0; v < numProductViews; v++) {
    const product = pick(PRODUCTS);

    await track(userId, sessionId, EVENTS.PAGE_VIEW, {
      ...baseMeta,
      page: `/product/${product.productId}`,
    });
    await sleep(50);

    await track(userId, sessionId, EVENTS.PRODUCT_VIEW, {
      ...baseMeta,
      page: `/product/${product.productId}`,
      productId: product.productId,
      productName: product.productName,
      price: product.price,
      category: product.category,
    });
    await sleep(100);

    if (chance(35)) {
      cart.push(product);
      await track(userId, sessionId, EVENTS.ADD_TO_CART, {
        ...baseMeta,
        productId: product.productId,
        productName: product.productName,
        price: product.price,
      });
      await sleep(100);

      if (chance(20)) {
        cart.pop();
        await track(userId, sessionId, EVENTS.REMOVE_FROM_CART, {
          ...baseMeta,
          productId: product.productId,
          productName: product.productName,
          price: product.price,
        });
        await sleep(100);
      }
    }
  }

  if (cart.length > 0) {
    if (chance(70)) {
      const cartTotal = cart.reduce((sum, p) => sum + p.price, 0);

      await track(userId, sessionId, EVENTS.CHECKOUT_START, {
        ...baseMeta,
        page: "/checkout",
        cartSize: cart.length,
        cartTotal: Math.round(cartTotal * 100) / 100,
      });
      await sleep(100);

      if (chance(60)) {
        await track(userId, sessionId, EVENTS.PURCHASE, {
          ...baseMeta,
          page: "/checkout/confirm",
          items: cart.map((p) => ({
            productId: p.productId,
            productName: p.productName,
            price: p.price,
          })),
          cartSize: cart.length,
          cartTotal: Math.round(cartTotal * 100) / 100,
        });
      }
    }
  }

  return counts;
}

// --- Run simulation ---

async function simulate() {
  console.log(`Simulating ${NUM_USERS} users...\n`);

  const eventCounts = {};

  for (let i = 1; i <= NUM_USERS; i++) {
    const counts = await simulateUser(i);
    for (const [event, count] of Object.entries(counts)) {
      eventCounts[event] = (eventCounts[event] || 0) + count;
    }
    await sleep(200);
  }

  console.log("\n\n\n");
  console.log("NUM_USERS:", NUM_USERS);
  console.log("\n--- Event Summary ---");
  for (const [event, count] of Object.entries(eventCounts)) {
    console.log(`  ${event}: ${count}`);
  }
  console.log("\nDone.");
}

simulate();
